import sbt._
import net.usersource.jettyembed._

class FermataProject(info: ProjectInfo) extends JettyEmbedWebProject(info) {
  val liftVersion = "2.1"
  val stScalaToolsReleases = "Sonatype Scala-Tools Maven2 Snapshots Repository" at "https://oss.sonatype.org/content/groups/scala-tools/"
  val stScalaToolsSnapshots = "Sonatype Scala-Tools Maven2 Snapshots Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
  val scalaToolsSnapshots = "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots/"
  val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.26" % "test->default",
    "junit" % "junit" % "4.7" % "test->default",
    "org.dbunit" % "dbunit" % "2.4.8" % "test->default",
    "com.h2database" % "h2" % "1.2.138",
    "org.subethamail" % "subethasmtp" % "3.1.4",
    "commons-io" % "commons-io" % "1.4",
    "org.apache.lucene" % "lucene-core" % "3.0.2",
    "org.apache.lucene" % "lucene-snowball" % "3.0.2"
  ) ++ super.libraryDependencies
}
