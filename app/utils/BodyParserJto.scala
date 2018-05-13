package utils

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Left, Right}

import json.GenericRulesWrites
import jto.validation.{From, RuleLike}
import play.api.libs.json.JsValue
import play.api.mvc.{BaseController, BodyParser, Result}

trait BodyParserJto extends BaseController with GenericRulesWrites {

  def json[A](implicit rule: RuleLike[JsValue, A]): BodyParser[A] = {
    parse.when(_.contentType.exists(m => m.equalsIgnoreCase("text/json") || m.equalsIgnoreCase("application/json")),
      BodyParser("parse json") { implicit request =>
        parse.tolerantJson(request).map {
          case Left(errors) => Left(errors)
          case Right(jsValue) => parseJsonEntityOr422(jsValue)(rule)
        }
      },
      _ => Future.successful(NotAcceptable("expecting.text/json.or.application/json"))
    )
  }

  def parseJsonEntityOr422[A](json: JsValue)(implicit rule: RuleLike[JsValue, A]): Either[Result, A] = {
    From[JsValue, A](json).fold(
      errors => {
        Left(UnprocessableEntity(errors.map(_._1.toString()).mkString("")))//errors.map(_._2.map(_.message).mkString("")).mkString("")))
      },
      entity => Right(entity)
    )
  }

}
