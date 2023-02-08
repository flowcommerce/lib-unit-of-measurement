// Comment to get more information during initialization
logLevel := Level.Warn

// Artifactory credentials
credentials += Credentials(Path.userHome / ".ivy2" / ".artifactory")

resolvers += "Artifactory" at "https://flow.jfrog.io/flow/libs-release-local/"

addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.2")


resolvers += "Flow Plugins" at "https://flow.jfrog.io/flow/plugins-release/"

addSbtPlugin("io.flow" % "sbt-flow-linter" % "0.0.36")

addSbtPlugin("com.github.sbt" % "sbt-git" % "2.0.1")

