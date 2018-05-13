package controllers

import java.sql.Date
import scala.concurrent.ExecutionContext

import entities.Cat
import services.CatsService
import utils.{BodyParserJto, CatId, ApiError, OwnerId}

import json.CatRule._
import jto.validation.To
import play.api.libs.json.JsValue
import play.api.mvc.ControllerComponents

class CatsController(
  catsService: CatsService,
  override val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext) extends BodyParserJto {

  def buy(catId: CatId, ownerId: OwnerId) = Action.async { implicit request =>
    catsService.buy(catId, ownerId).map {
      case Right(_) => Created
      case Left(error) => Conflict(To[ApiError, JsValue](error))
    }
  }

  def create() = Action.async(json[Cat]) { implicit request =>
    catsService.create(request.body).map {
      case Right(_) => Created
      case Left(error) => Conflict(To[ApiError, JsValue](error))
    }
  }

  def death(catId: CatId, dateOdDeath: Date) = Action.async  { implicit request =>
    catsService.death(catId, dateOdDeath).map {
      case Right(_) => Created
      case Left(error) => Conflict(To[ApiError, JsValue](error))
    }
  }

  def get(catId: CatId) = Action.async { implicit request =>
    catsService.get(catId).map {
      case Some(cat) => Ok(To[Cat, JsValue](cat))
      case None => NotFound
    }
  }

}
