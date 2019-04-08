name := "lib-unit-of-measurement"

organization := "io.flow"

scalaVersion in ThisBuild := "2.12.8"

crossScalaVersions := Seq("2.12.8")

version := "0.0.92"

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.10.1",
      "org.joda" % "joda-convert" % "2.1.2",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      compilerPlugin("com.github.ghik" %% "silencer-plugin" % "1.3.0"),
      "com.github.ghik" %% "silencer-lib" % "1.3.0" % Provided,
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

// silence all warnings on autogenerated files
flowGeneratedFiles ++= Seq(
  "src/main/scala/io/generated/.*".r,
)

// Make sure you only exclude warnings for the project directories, i.e. make builds reproducible
scalacOptions += s"-P:silencer:sourceRoots=${baseDirectory.value.getCanonicalPath}"

