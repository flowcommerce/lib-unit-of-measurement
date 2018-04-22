name := "lib-unit-of-measurement"

organization := "io.flow"

scalaVersion in ThisBuild := "2.12.5"

crossScalaVersions := Seq("2.12.5", "2.11.12")

version := "0.0.54"

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.9",
      "org.joda" % "joda-convert" % "2.0.1",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    )
)
    credentials += Credentials(
      "Artifactory Realm",
      "flow.artifactoryonline.com",
      System.getenv("ARTIFACTORY_USERNAME"),
      System.getenv("ARTIFACTORY_PASSWORD")
    )

publishTo := {
  val host = "https://flow.artifactoryonline.com/flow"
  if (isSnapshot.value) {
    Some("Artifactory Realm" at s"$host/libs-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
  } else {
    Some("Artifactory Realm" at s"$host/libs-release-local")
  }
}
