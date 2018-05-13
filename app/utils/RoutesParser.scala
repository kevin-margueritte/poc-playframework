package utils

import java.util.UUID
import scala.util.Try

import play.api.mvc.PathBindable

object RoutesParser {

  implicit object parameterBindableCatId extends PathBindable[CatId] {
    override def bind(key: String, catIdStr: String): Either[String, CatId] = {
      Try(CatId(UUID.fromString(catIdStr))).toEither match {
        case Right(catId) => Right(catId)
        case Left(e) => Left(s"Cannot parse parameter $key as CatId: $e")
      }
    }

    override def unbind(key: String, catId: CatId): String = catId.id.toString
  }

  implicit object parameterBindableOwnerId extends PathBindable[OwnerId] {
    override def bind(key: String, ownerIdStr: String): Either[String, OwnerId] = {
      Try(OwnerId(UUID.fromString(ownerIdStr))).toEither match {
        case Right(ownerId) => Right(ownerId)
        case Left(e) => Left(s"Cannot parse parameter $key as OwnerId: $e")
      }
    }

    override def unbind(key: String, ownerId: OwnerId): String = ownerId.id.toString
  }

}
