import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val jettyEmbeddedWarRepo = "Embeded Jetty Repo" at "https://github.com/glenford/repo/raw/master"
  val jettyEmbeddedWar = "net.usersource" % "sbt-jetty-embed-plugin" % "0.6.1"
}
