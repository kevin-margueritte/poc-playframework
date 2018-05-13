package daos

import java.sql.Date

import entities.{Cat, Void}
import entities.mappers.EntityMappers
import utils.{CatId, OwnerId}

import slick.dbio.DBIO

trait CatsDAO extends EntityMappers {

  def create(cat: Cat): DBIO[Void]

  def updateOwner(catId: CatId, ownerId: OwnerId): DBIO[Void]

  def updateDateOfDeath(catId: CatId, date: Date): DBIO[Void]

  def delete(catId: CatId): DBIO[Void]

  def getByCatId(catId: CatId): DBIO[Option[Cat]]

  def getByPedigreeName(name: String): DBIO[Seq[Cat]]

}
