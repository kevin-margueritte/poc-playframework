import sbt._

object Dependencies {

  object Repositories {
    val sonatypeOss = "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  }

  object Version {
    val playVersion       = "2.6.14"
    val scalatestVersion  = "3.1.2"
    val scalaCheckVersion = "1.14.0"
    val slickVersion      = "3.0.0"
    val jtoVersion        = "2.1.0"
    val enumeratumVersion = "1.5.13"
    val macWireVersion    = "2.3.0"
  }

  object Librairies {
    val scalaTest       = "org.scalatestplus.play"   %% "scalatestplus-play"      % Version.scalatestVersion  % Test
    val scalaCheck      = "org.scalacheck"           %% "scalacheck"              % Version.scalaCheckVersion % Test
    val slick           = "com.typesafe.play"        %% "play-slick"              % Version.slickVersion
    val slickEvolution  = "com.typesafe.play"        %% "play-slick-evolutions"   % Version.slickVersion
    val enumeratum      = "com.beachape"             %% "enumeratum"              % Version.enumeratumVersion
    val database        = "com.h2database"           % "h2"                       % "1.4.197"
    val macwireMacro    = "com.softwaremill.macwire" %% "macros"                  % Version.macWireVersion    % Provided
    val jto             = Seq(
      "io.github.jto" %% "validation-core",
      "io.github.jto" %% "validation-playjson"
    ).map(_ % Version.jtoVersion)
    val macwire         = Seq(
      "com.softwaremill.macwire" %% "util",
      "com.softwaremill.macwire" %% "proxy"
    ).map(_ % Version.macWireVersion)
  }

}
