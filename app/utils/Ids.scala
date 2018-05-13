package utils

import java.util.UUID

sealed trait Id {
  val id: UUID
}

case class CatId(id: UUID)      extends Id
case class OwnerId(id: UUID)    extends Id
case class PedigreeId(id: UUID) extends Id