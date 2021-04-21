enablePlugins(SbtPlugin)

name := """sbt-buildinfo"""
description := "Clone of com.eed3si9n.sbt-buildinfo"
version := "0.10.0-SNAPSHOT"
organization := "co.tala"
scalaVersion := "2.12.8"
crossScalaVersions := Seq(scalaVersion.value)
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided

scalacOptions := Seq(
    "-Xlint",
    "-Xfatal-warnings",
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-language:experimental.macros"
)

publishTo := {
    val nexus = "https://tala.jfrog.io/tala/"
    if (isSnapshot.value) {
        Some("snapshots" at nexus + "tala-bintray-cache-snapshot")
    } else {
        Some("releases" at nexus + "tala-bintray-cache-release")
    }
}
