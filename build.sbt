name := "lib-unit-of-measurement"

organization := "io.flow"

scalaVersion := "2.13.1"

version := "0.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.10.6",
      "org.joda" % "joda-convert" % "2.2.1",
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      compilerPlugin("com.github.ghik" %% "silencer-plugin" % "1.6.0" cross CrossVersion.full),
      "com.github.ghik" %% "silencer-lib" % "1.6.0" % Provided cross CrossVersion.full,
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

