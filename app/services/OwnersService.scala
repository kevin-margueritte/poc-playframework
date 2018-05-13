package services

import scala.concurrent.Future
import entities.{Cat, Owner}
import utils.OwnerId

trait OwnersService {

  def createOwner(owner: Owner): Future[Owner]

  def getCats(ownerId: OwnerId): Future[Seq[Cat]]

}
