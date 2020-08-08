lazy val check = taskKey[Unit]("checks this plugin")

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    name := "helloworld",
    version := "0.1",
    scalaVersion := "2.12.7",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.0",
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      BuildInfoKey.map(version) { case (n, v) => "projectVersion" -> v.toDouble },
      scalaVersion,
      ivyXML,
      homepage,
      licenses,
      apiMappings,
      isSnapshot,
      "year" -> 2012,
      "sym" -> 'Foo,
      BuildInfoKey.action("buildTime") { 1234L },
      target
    ),
    buildInfoPackage := "foo.bar.baz",
    buildInfoUsePackageAsPath := true,
    homepage := Some(url("http://example.com")),
    licenses := Seq("MIT License" -> url("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE")),
    check := {
      val f = (sourceManaged in Compile).value / "foo" / "bar" / "baz" / ("%s.scala" format "BuildInfo")
      val lines = scala.io.Source.fromFile(f).getLines.toList
      lines match {
        case """// $COVERAGE-OFF$""" ::
             """package foo.bar.baz""" ::
             """""" ::
             """import scala.Predef._""" ::
             """""" ::
             """/** This object was generated by sbt-buildinfo. */""" ::
             """case object BuildInfo {""" ::
             """  /** The value is "helloworld". */"""::
             """  val name: String = "helloworld"""" ::
             """  /** The value is 0.1. */"""::
             """  val projectVersion = 0.1""" ::
             """  /** The value is "2.12.7". */""" ::
             """  val scalaVersion: String = "2.12.7"""" ::
             """  /** The value is scala.collection.immutable.Seq(). */""" ::
             """  val ivyXML: scala.xml.NodeSeq = scala.collection.immutable.Seq()""" ::
             """  /** The value is scala.Some(new java.net.URL("http://example.com")). */""" ::
             """  val homepage: scala.Option[java.net.URL] = scala.Some(new java.net.URL("http://example.com"))""" ::
             """  /** The value is scala.collection.immutable.Seq(("MIT License" -> new java.net.URL("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE"))). */""" ::
             """  val licenses: scala.collection.immutable.Seq[(String, java.net.URL)] = scala.collection.immutable.Seq(("MIT License" -> new java.net.URL("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE")))""" ::
             """  /** The value is Map(). */""" ::
             """  val apiMappings: Map[java.io.File, java.net.URL] = Map()""" ::
             """  /** The value is false. */""" ::
             """  val isSnapshot: scala.Boolean = false""" ::
             """  /** The value is 2012. */""" ::
             """  val year: scala.Int = 2012""" ::
             """  /** The value is 'Foo. */""" ::
             """  val sym: scala.Symbol = 'Foo""" ::
             """  /** The value is 1234L. */""" ::
             """  val buildTime: scala.Long = 1234L""" ::
             targetInfoComment ::
             targetInfo :: // """
             """  override val toString: String = {""" ::
             """    "name: %s, projectVersion: %s, scalaVersion: %s, ivyXML: %s, homepage: %s, licenses: %s, apiMappings: %s, isSnapshot: %s, year: %s, sym: %s, buildTime: %s, target: %s".format(""" ::
             """      name, projectVersion, scalaVersion, ivyXML, homepage, licenses, apiMappings, isSnapshot, year, sym, buildTime, target""" ::
             """    )""" ::
             """  }""" ::
             """}""" ::
             """// $COVERAGE-ON$""" :: Nil if (targetInfo contains "val target: java.io.File = new java.io.File(") =>
        case _ => sys.error("unexpected output: \n" + lines.mkString("\n"))
      }
      ()
    }
  )
