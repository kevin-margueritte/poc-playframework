package utils

import com.typesafe.config.ConfigFactory
import di.PocPlayComponents
import play.api.ApplicationLoader.Context

class TestComponents(context: Context) extends PocPlayComponents(context: Context) {

  /*override lazy val configuration = {
    val databseConfig = ConfigFactory.parseString(
      """slick.dbs.default.db.url = <database test>
        |slick.dbs.default.db.user = <user>
        |slick.dbs.default.db.password = <password>
      """.stripMargin)

    context.initialConfiguration.copy(underlying = databseConfig)
  }*/
}
