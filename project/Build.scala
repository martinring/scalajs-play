import sbt._
import play.Project._
import scalajs.sbtplugin.ScalaJSPlugin._
import Keys._
import ScalaJSKeys._
import ScalaJSPlay._
import play.Project._

object Build extends Build {
  val namePrefix = "scalajs-play"
  
  val sharedSources = file("shared")

  val commonSettings = Seq(
    version := "0.1-SNAPSHOT",
    unmanagedSourceDirectories in Compile += sharedSources
  )

  val client = project.in(file("client"))
    .settings(scalaJSSettings ++ commonSettings:_*)
    .settings(
      name := namePrefix + "-client",
      libraryDependencies += "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.2"
    )
  
  val server = project.in(file("server"))
    .settings(playScalaSettings ++ commonSettings:_*)
    .dependsOnJs(client -> "main.js")
    .settings(
      name := namePrefix + "-server"
    )

  // override rootProject in order to have the play project as the root project
  override def rootProject = Some(server)
}