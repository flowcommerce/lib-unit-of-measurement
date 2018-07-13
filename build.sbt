name := "lib-unit-of-measurement"

organization := "io.flow"

scalaVersion in ThisBuild := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.12")

version := "0.0.67"

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.10",
      "org.joda" % "joda-convert" % "2.1.1",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    )
)
    credentials += Credentials(
      "Artifactory Realm",
      "flow.jfrog.io",
      System.getenv("ARTIFACTORY_USERNAME"),
      System.getenv("ARTIFACTORY_PASSWORD")
    )

publishTo := {
  val host = "https://flow.jfrog.io/flow"
  if (isSnapshot.value) {
    Some("Artifactory Realm" at s"$host/libs-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
  } else {
    Some("Artifactory Realm" at s"$host/libs-release-local")
  }
}
