package json

import cats.data.Validated.{Invalid, Valid}
import entities.Gender

import jto.validation.{Rule, ValidationError, Write}
import play.api.libs.json.{JsString, JsValue}

object GenderRule extends GenericRulesWrites {

  implicit val genderWrite: Write[Gender, JsValue] =
    Write[Gender, JsValue](gender => JsString(gender.entryName))

  implicit val genderRead: Rule[JsValue, Gender] = Rule.fromMapping[JsValue, Gender] {
    case JsString(genderStr) =>
      Gender.withNameOption(genderStr) match {
        case Some(gender) => Valid(gender)
        case None => Invalid(Seq(ValidationError("error.invalid", "gender")))
      }
    case _ => Invalid(Seq(ValidationError("error.invalid", "json.string")))
  }
}
