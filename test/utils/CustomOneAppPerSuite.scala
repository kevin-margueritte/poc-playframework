package utils

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.{ApplicationLoader, Environment, Mode}

trait PocPlayOneAppPerSuite extends PlaySpec with GuiceOneAppPerSuite { self =>

  override lazy val app = {
    val context = ApplicationLoader.createContext(new Environment(new java.io.File("."), ApplicationLoader.getClass.getClassLoader, Mode.Test))
    new TestComponents(context).application
  }
}
