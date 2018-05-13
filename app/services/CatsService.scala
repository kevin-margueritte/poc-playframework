package services

import java.sql.Date
import scala.concurrent.Future

import entities.{Cat, Void}
import utils.{CatId, ApiError, OwnerId}

trait CatsService {

  def buy(catId: CatId, ownerId: OwnerId): Future[Either[ApiError, Void]]

  def create(cat: Cat): Future[Either[ApiError, Void]]

  def death(catId: CatId, dateOfDeath: Date): Future[Either[ApiError, Void]]

  def get(id: CatId): Future[Option[Cat]]

  def getByPedigreeName(name: String): Future[Seq[Cat]]
}
