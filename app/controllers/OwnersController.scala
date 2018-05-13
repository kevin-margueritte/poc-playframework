package controllers

import scala.concurrent.ExecutionContext

import entities.Cat
import services.OwnersService
import utils.{BodyParserJto, OwnerId}

import play.api.libs.json.JsValue
import play.api.mvc.ControllerComponents
import json.CatRule._
import jto.validation.To

class OwnersController (
  ownerService: OwnersService,
  override val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext) extends BodyParserJto {

  def cats(ownerId: OwnerId) = Action.async { implicit request =>
    ownerService.getCats(ownerId).map { case cats =>
      Ok(To[Seq[Cat], JsValue](cats))
    }
  }

}
