enablePlugins(SbtPlugin)

name := """sbt-buildinfo"""
description := "Clone of com.eed3si9n.sbt-buildinfo"
version := "0.10.0-SNAPSHOT"
organization := "co.tala"
scalaVersion := "2.12.8"
crossScalaVersions := Seq(scalaVersion.value)
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers ++= Seq(
    "Tala Atlas Artifactory Snapshots" at "https://tala.jfrog.io/tala/maven-snapshot-local",
    "Tala Atlas Artifactory Releases" at "https://tala.jfrog.io/tala/maven-release-local"
)

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

// The generated POM has a conflict, but this is not needed for this temporary solution
publishArtifact in makePom := false

publishTo := {
    val nexus = "https://tala.jfrog.io/tala/"
    if (isSnapshot.value) {
        Some("snapshots" at nexus + "maven-snapshot-local")
    } else {
        Some("releases" at nexus + "maven-release-local")
    }
}
