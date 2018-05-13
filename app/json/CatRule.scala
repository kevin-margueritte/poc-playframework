package json

import java.sql.Date
import java.util.UUID

import entities.{Cat, Gender}
import utils.{CatId, OwnerId, PedigreeId}
import jto.validation.{From, To, Write}
import play.api.libs.json.{JsArray, JsValue, Json}

object CatRule extends GenericRulesWrites {

  import jto.validation.playjson.Rules._
  import GenderRule._

  implicit val arrayCatWrite: Write[Seq[Cat], JsValue] = Write[Seq[Cat], JsValue] { cats =>
    JsArray(cats.map(cat => To[Cat, JsValue](cat)))
  }

  implicit val catWrite: Write[Cat, JsValue] = Write[Cat, JsValue] { cat =>
    Json.obj(
      "id"          -> cat.catId.id,
      "name"        -> cat.name,
      "pedigreeId"  -> cat.pedigree.id,
      "gender"      -> cat.gender.enumEntry.entryName,
      "ownerId"     -> cat.owner.map(_.id),
      "dateOfBirth" -> cat.dateOfBirth,
      "dateOfDeath" -> cat.dateOfDeath
    )
  }

  implicit val createCatJson = From[JsValue] { __ =>
    (
        (__ \ "name")       .read[String] ~
        (__ \ "pedigree")   .read[PedigreeId] ~
        (__ \ "gender")     .read[Gender] ~
        (__ \ "ownerId")    .read[Option[OwnerId]] ~
        (__ \ "dateOfBirth").read[Date] ~
        (__ \ "dateOfDeath").read[Option[Date]]
    )((name, pedigreeId, gender, ownerId, birth, death) =>
      Cat(
        catId = CatId(UUID.randomUUID()),
        pedigree = pedigreeId,
        name = name,
        gender = gender,
        owner = ownerId,
        dateOfBirth = birth,
        dateOfDeath = death
      )
    )
  }
}
