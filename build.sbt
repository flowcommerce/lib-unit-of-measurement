name := "lib-unit-of-measurement"

organization := "io.flow"

scalaVersion in ThisBuild := "2.11.11"

crossScalaVersions := Seq("2.11.11","2.12.3")

version := "0.1.0-SNAPSHOT"

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.9",
      "org.joda" % "joda-convert" % "1.8.3",
      "org.scalatest" %% "scalatest" % "3.0.4" % "test"
    )
)

publishTo := {
  val host = "https://flow.artifactoryonline.com/flow"
  if (isSnapshot.value) {
    Some("Artifactory Realm" at s"$host/libs-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
  } else {
    Some("Artifactory Realm" at s"$host/libs-release-local")
  }
}
