package daos.impl

import java.util.UUID

import daos.OwnersDAO
import entities.{Cat, Owner, Schemas}
import utils.OwnerId
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext

class OwnersDAOSlick()(implicit ec: ExecutionContext) extends OwnersDAO {

  private val table = Schemas.owners

  override def createOwner(owner: Owner): DBIO[Owner] = {
    val returnQuery = table returning table into ((_, owner) => owner)

    val ownerIdGen = OwnerId(UUID.randomUUID())
    returnQuery += owner
  }

  override def getCatsByOwnerId(ownerId: OwnerId): DBIO[Seq[Cat]] = {
    queryGetCatsByOwnerId.extract(Some(ownerId)).result
  }

  private val queryGetCatsByOwnerId = Compiled((ownerId: Rep[Option[OwnerId]]) =>
    Schemas.cats.filter(_.owner === ownerId)
  )

  private val queryGetById = Compiled((id: Rep[OwnerId]) =>
    table.filter(_.ownerId === id)
  )

}
