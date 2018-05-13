package binder

import java.util.UUID
import scala.util.{Left, Right, Try}

import play.api.mvc.PathBindable
import utils.OwnerId

object PathBuilder {

  implicit def ownerId(implicit stringBinder: PathBindable[String]) = new PathBindable[OwnerId] {

    def bind(key: String, value: String): Either[String, OwnerId] = {
      val idStr = stringBinder.bind(key, value).right.get
      Try(OwnerId(UUID.fromString(idStr))).toOption match {
        case Some(ownerId) => Right(ownerId)
        case None => Left("OwnerId provided is malformed")
      }
    }

    def unbind(key: String, owner: OwnerId): String =
      stringBinder.unbind(key, owner.id.toString())

  }

}
