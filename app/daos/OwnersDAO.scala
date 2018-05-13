package daos

import entities.{Cat, Owner}
import entities.mappers.EntityMappers
import slick.dbio.DBIO
import utils.OwnerId

trait OwnersDAO extends EntityMappers {
  def createOwner(owner: Owner): DBIO[Owner]

  def getCatsByOwnerId(ownerId: OwnerId): DBIO[Seq[Cat]]
}
