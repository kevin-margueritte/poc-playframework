package services.impl

import scala.concurrent.{ExecutionContext, Future}

import daos.OwnersDAO
import entities.{Cat, Owner}
import services.OwnersService
import utils.{DbExecutor, OwnerId}

class OwnerServiceImpl(
  ownerDAO: OwnersDAO,
  db: DbExecutor
)(implicit ec: ExecutionContext) extends OwnersService {

  override def createOwner(owner: Owner): Future[Owner] = {
    db.run(ownerDAO.createOwner(owner))
  }

  override def getCats(ownerId: OwnerId): Future[Seq[Cat]] = {
    db.run(ownerDAO.getCatsByOwnerId(ownerId))
  }
}
