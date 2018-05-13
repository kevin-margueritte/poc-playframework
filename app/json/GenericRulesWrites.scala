package json

import java.sql.Date
import java.util.UUID
import scala.util.Try

import cats.data.Validated.{Invalid, Valid}
import utils.{CatId, ApiError, OwnerId, PedigreeId}

import jto.validation.{Rule, ValidationError, Write}
import org.apache.commons.lang3.time.DateFormatUtils
import play.api.libs.json.{JsString, JsValue, Json}


trait GenericRulesWrites {

  private val dateFormat = DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT
  implicit val dateWrites: Write[Date, JsValue] = Write[Date, JsValue]{ date =>
    JsString(dateFormat.format(date))
  }
  implicit val dateRules: Rule[JsValue, Date] = Rule.fromMapping[JsValue, Date] {
    case JsString(dateStr) =>
      Try(dateFormat.parse(dateStr)).toOption match {
        case Some(date) => Valid(new Date(date.getTime))
        case None => Invalid(Seq(ValidationError("error.invalid", "date")))
      }
    case _ => Invalid(Seq(ValidationError("error.invalid", "date")))
  }

  implicit val errorsWrites: Write[ApiError, JsValue] = Write[ApiError, JsValue] { errors =>
    Json.obj("error" -> errors.error)
  }

  implicit val errorRules: Rule[JsValue, ApiError] = Rule.fromMapping[JsValue, ApiError] {
    case JsString(errorsStr) => Valid(ApiError(errorsStr))
    case _ => Invalid(Seq(ValidationError("error.invalid", "JsString")))
  }

  implicit val uuidWrites: Write[UUID, JsValue] = Write[UUID, JsValue](uuid => JsString(uuid.toString))
  implicit val uuidRules: Rule[JsValue, UUID] = Rule.fromMapping[JsValue, UUID] {
    case JsString(uuidStr) =>
      Try(UUID.fromString(uuidStr)).toOption match {
        case Some(uuid) => Valid(uuid)
        case None => Invalid(Seq(ValidationError("error.invalid", "UUID")))
      }
    case _ => Invalid(Seq(ValidationError("error.invalid", "UUID")))
  }
  def fromUUID[T](f: UUID => T): Rule[JsValue, T] = uuidRules.map(f)
  def toUUID[T](f: T => UUID): Write[T, JsValue] = uuidWrites.contramap(f)

  implicit val catIdWrites: Write[CatId, JsValue] = toUUID[CatId](_.id)
  implicit val catIdRules: Rule[JsValue, CatId] = fromUUID(CatId)

  implicit val pedigreeIdWrites: Write[PedigreeId, JsValue] = toUUID[PedigreeId](_.id)
  implicit val pedigreeIdRules: Rule[JsValue, PedigreeId] = fromUUID(PedigreeId)

  implicit val ownerIdWrites: Write[OwnerId, JsValue] = toUUID[OwnerId](_.id)
  implicit val ownerIdRules: Rule[JsValue, OwnerId] = fromUUID(OwnerId)

}
