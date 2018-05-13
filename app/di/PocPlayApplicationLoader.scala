package di

import daos.{CatsDAO, OwnersDAO}
import daos.impl.{CatsDAOSlick, OwnersDAOSlick}
import _root_.controllers.{CatsController, OwnersController}
import router.Routes
import services.{CatsService, OwnersService}
import services.impl.{CatsServiceImpl, OwnerServiceImpl}
import slick.jdbc.JdbcProfile
import utils.DbExecutor

import play.api._
import play.api.ApplicationLoader.Context
import play.api.db.DBApi
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.slick.evolutions.SlickDBApi
import play.api.db.slick.{DbName, SlickComponents}
import play.api.routing.Router
import play.filters.HttpFiltersComponents

class PocPlayApplicationLoader extends ApplicationLoader {
  override def load(context: Context): Application = {
    new PocPlayComponents(context).application
  }
}

class PocPlayComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with HttpFiltersComponents
  with SlickComponents
  with EvolutionsComponents {

  import com.softwaremill.macwire._

  // Database injection, new since Play 2.6
  override lazy val dbApi: DBApi = SlickDBApi(slickApi)
  lazy val db: DbExecutor = slickApi.dbConfig[JdbcProfile](DbName("default")).db
  def boot() = {
    applicationEvolutions.start()
  }
  boot()

  // Routers
  override lazy val router: Router = {
    lazy val prefix: String = "/"

    wire[Routes]
  }


  // DAOs
  lazy val catsDAO: CatsDAO     = new CatsDAOSlick()
  lazy val ownersDAO: OwnersDAO = new OwnersDAOSlick()

  // Services
  lazy val catsService: CatsService     = wire[CatsServiceImpl]
  lazy val ownerService: OwnersService  = wire[OwnerServiceImpl]

  //Controllers
  lazy val catsController: CatsController     = wire[CatsController]
  lazy val ownersController: OwnersController = wire[OwnersController]

}
