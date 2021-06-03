name := "lib-unit-of-measurement"

organization := "io.flow"

scalaVersion := "2.13.5"

lazy val allScalacOptions = Seq(
  "-feature",
  "-Xfatal-warnings",
  "-unchecked",
  "-Xcheckinit",
  "-Xlint:adapted-args",
  "-Ypatmat-exhaust-depth", "100", // Fixes: Exhaustivity analysis reached max recursion depth, not all missing cases are reported.
  "-Wconf:src=generated/.*:silent",
  "-Wconf:src=target/.*:silent", // silence the unused imports errors generated by the Play Routes
)

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.10.10",
      "org.joda" % "joda-convert" % "2.2.1",
      "org.scalatest" %% "scalatest" % "3.2.9" % Test,
      "org.scalatest" %% "scalatest-funspec" % "3.2.9" % Test,
      "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.9" % Test,
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

scalacOptions ++= allScalacOptions

