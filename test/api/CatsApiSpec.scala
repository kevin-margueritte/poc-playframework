package api

import java.util.UUID

import entities.Gender
import utils.{OwnerId, PedigreeId, PocPlayOneAppPerSuite}
import org.scalatest.prop.Checkers._
import play.api.test.Helpers._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalacheck._
import Gen._
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.test.FakeRequest

class CatsApiSpec extends PocPlayOneAppPerSuite {

  def createCatApi(body: JsValue) = FakeRequest("POST", "/api/cats").withBody(body)

  "Cats API" must {

    def genGender: Gen[Gender]          = Gen.oneOf(const(Gender.Female), const(Gender.Male))
    def ownerIdGen: Gen[OwnerId]        = const(OwnerId(UUID.fromString("76065e7d-8604-4995-a6ed-33fca30517aa")))
    def dateGen: Gen[DateTime]          = const(DateTime.now())
    def pedigreeIdGen: Gen[PedigreeId]  = {
      val id1 = PedigreeId(UUID.fromString("1f7bf0e2-612d-11e8-9c2d-fa7ae01bbebc"))
      val id2 = PedigreeId(UUID.fromString("cc58095b-8d51-4a22-a110-7ace2d921c2c"))
      Gen.oneOf(const(id1), const(id2))
    }

    def catJsonGenerator: Gen[JsObject] = {
      for {
        name    <- Gen.alphaStr.suchThat(_.size < 32)
        gender  <- Gen.oneOf(const(Gender.Female), const(Gender.Male))
        pedigreeId <- const(PedigreeId(UUID.fromString("cc58095b-8d51-4a22-a110-7ace2d921c2c")))
        ownerId <- option(const(OwnerId(UUID.fromString("76065e7d-8604-4995-a6ed-33fca30517aa"))))
        dateOfBirth <- const(DateTime.now())
      } yield buildCatJson(name, gender, pedigreeId, ownerId, dateOfBirth, None)
    }

    def buildCatJson(name: String, gender: Gender, pedigreeId: PedigreeId, ownerId: Option[OwnerId], dateOfBirth: DateTime, dateOfDeath: Option[DateTime]) = {
      val formater = DateTimeFormat.forPattern("yyyy-MM-dd")

      Json.obj(
        "name"        -> name,
        "gender"      -> gender.entryName,
        "pedigree"    -> pedigreeId.id.toString,
        "ownerId"     -> ownerId.map(_.id.toString),
        "dateOfBirth" -> formater.print(dateOfBirth),
        "dateOfDeath" -> dateOfDeath.map(formater.print(_))
      )
    }

    "have a valid endpoint `create cat API`" in {
      check {
        Prop.forAll(catJsonGenerator) { catsBody: JsObject =>
          val Some(response) = route(app, createCatApi(catsBody))
          status(response) == CREATED
        }
      }
    }
  }
}
