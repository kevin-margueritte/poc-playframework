package daos.impl

import java.sql.Date

import scala.concurrent.ExecutionContext
import daos.CatsDAO
import entities.{Cat, Void, Schemas}
import utils.{CatId, OwnerId}
import slick.jdbc.H2Profile.api._

case class CatsDAOSlick()(implicit ec: ExecutionContext) extends CatsDAO {

  private val table = Schemas.cats

  override def create(cat: Cat): DBIO[Void] =
    (table += cat).map(_ => Void)

  override def updateOwner(catId: CatId, ownerId: OwnerId): DBIO[Void] = {
    queryGetById.extract(catId).map(_.owner).update(Some(ownerId)).map(_ => Void)
  }

  override def updateDateOfDeath(catId: CatId, date: Date): DBIO[Void] = {
    queryGetById.extract(catId).map(_.dateOfDeath).update(Some(date)).map(_ => Void)
  }

  override def delete(catId: CatId): DBIO[Void] = {
    table.filter(_.catId === catId).delete.map(_ => Void)
  }

  override def getByCatId(catId: CatId): DBIO[Option[Cat]] = {
    queryGetById.extract(catId).result.headOption
  }

  override def getByPedigreeName(name: String): DBIO[Seq[Cat]] = {
    queryGetByPedigreeName.extract(name).result
  }

  private val queryGetById = Compiled((id: Rep[CatId]) =>
    table.filter(_.catId === id)
  )

  private val queryGetByPedigreeName = Compiled((pedigreeName: Rep[String]) =>
    for {
      cat <- Schemas.cats
      pedigree <- Schemas.pedigress if pedigree.id === cat.pedigreeId && pedigree.pedigree === pedigreeName
    } yield cat
  )
}
