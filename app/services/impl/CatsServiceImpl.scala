package services.impl

import java.sql.Date
import scala.concurrent.{ExecutionContext, Future}

import daos.CatsDAO
import entities.{Cat, Void}
import services.CatsService
import utils.{ApiError, CatId, DbExecutor, OwnerId}

import slick.dbio.DBIO
import slick.jdbc.H2Profile.api._

class CatsServiceImpl(
 catsDAO: CatsDAO,
 db: DbExecutor
)(implicit ec: ExecutionContext) extends CatsService {

  override def buy(catId: CatId, ownerId: OwnerId): Future[Either[ApiError, Void]] = {
    for {
      catOpt <- db.run(catsDAO.getByCatId(catId))
      result <- catOpt match {
        case None => Future.successful(Left(ApiError.Cats.catNotFound))
        case Some(cat) if cat.owner.isDefined => Future.successful(Left(ApiError.Cats.catAlreadyAdopted))
        case _ => db.run(catsDAO.updateOwner(catId, ownerId)).map(Right.apply)
      }
    } yield result
  }

  override def create(cat: Cat): Future[Either[ApiError, Void]] = {
    cat.dateOfDeath match {
      case Some(dateOfDeath) => Future.successful(Left(ApiError.Cats.catsDead))
      case _ => db.run(catsDAO.create(cat).map(Right.apply))
    }
  }

  override def death(catId: CatId, dateOfDeath: Date): Future[Either[ApiError, Void]] = {
    def checkDateOfDeath(birth: Date, death: Date): Future[Either[ApiError, Void]] = {
      birth.before(death) match {
        case true => db.run(catsDAO.updateDateOfDeath(catId, dateOfDeath)).map(Right.apply)
        case false => Future.successful(Left(ApiError.Cats.catNotFound))
      }
    }

    for {
      catOpt <- db.run(catsDAO.getByCatId(catId))
      result <- catOpt match {
        case Some(cat) => checkDateOfDeath(cat.dateOfBirth, dateOfDeath)
        case None => Future.successful(Left(ApiError.Cats.catNotFound))
      }
    } yield result
  }

  override def getByPedigreeName(name: String): Future[Seq[Cat]] = {
    val transaction = (for {
      cat <- catsDAO.getByPedigreeName(name)
      _ <- DBIO.seq(cat.map(c => catsDAO.delete(c.catId)): _*)
    } yield cat).transactionally
    db.run(transaction)
  }

  override def get(catId: CatId): Future[Option[Cat]] = {
    db.run(catsDAO.getByCatId(catId))
  }

}
