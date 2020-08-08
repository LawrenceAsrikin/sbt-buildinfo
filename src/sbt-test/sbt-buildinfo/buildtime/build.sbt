import scala.collection.immutable.::

lazy val check = taskKey[Unit]("checks this plugin")

ThisBuild / scalaVersion := "2.12.12"
ThisBuild / organization := "com.example"
ThisBuild / version := "0.1"
ThisBuild / homepage := Some(url("http://example.com"))
ThisBuild / licenses := Seq("MIT License" -> url("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE"))

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "helloworld",
    buildInfoKeys := Seq(
      name,
      version,
      scalaVersion
    ),
    buildInfoPackage := "hello",
    buildInfoOptions := Seq(BuildInfoOption.BuildTime),
    scalacOptions ++= Seq("-Xlint", "-Xfatal-warnings", "-Yno-imports"),
    check := {
      val f = (sourceManaged in Compile).value / "sbt-buildinfo" / ("%s.scala" format "BuildInfo")
      val lines = scala.io.Source.fromFile(f).getLines.toList
      lines match {
        case """// $COVERAGE-OFF$""" ::
          """package hello""" ::
          """""" ::
          """import scala.Predef._""" ::
          """""" ::
          """/** This object was generated by sbt-buildinfo. */""" ::
          """case object BuildInfo {""" ::
          """  /** The value is "helloworld". */"""::
          """  val name: String = "helloworld"""" ::
          """  /** The value is "0.1". */"""::
          """  val version: String = "0.1"""" ::
          """  /** The value is "2.12.12". */""" ::
          """  val scalaVersion: String = "2.12.12"""" ::
          builtAtStringComment ::
          builtAtString ::
          builtAtMillisComment ::
          builtAtMillis ::
          """  override val toString: String = {""" ::
          """    "name: %s, version: %s, scalaVersion: %s, builtAtString: %s, builtAtMillis: %s".format(""" ::
          """      name, version, scalaVersion, builtAtString, builtAtMillis""" ::
          """    )""" ::
          """  }""" ::
          """}""" ::
          """// $COVERAGE-ON$""" :: Nil =>
        case _ => sys.error("unexpected output:\n" + lines.mkString("\n"))
      }
      ()
    }
  )
