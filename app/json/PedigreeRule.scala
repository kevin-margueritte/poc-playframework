package json

import scala.Function.unlift

import entities.Pedigree
import utils.PedigreeId

import jto.validation.To
import play.api.libs.json.JsObject

object PedigreeRule extends GenericRulesWrites{

  import jto.validation.playjson.Writes._

  implicit val pedigreeWriter = To[JsObject] { __ =>
    (
      (__ \ "pedigreeId") .write[PedigreeId] ~
      (__ \ "name")       .write[String]
    ) (unlift(Pedigree.unapply))
  }
}
