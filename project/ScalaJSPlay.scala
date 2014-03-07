import sbt._
import play.Project._
import scalajs.sbtplugin.ScalaJSPlugin._
import Keys._
import ScalaJSKeys._
import java.io.File

object ScalaJSPlay extends Plugin {
  implicit class ScalaJSPlayProject(val project: Project) {
    def dependsOnJs(references: (Project,String)*): Project =
      references.foldLeft(project){ case (project,(ref,name)) =>
        project.settings (
          resourceGenerators in Compile <+= (preoptimizeJS in (ref,Compile), resourceManaged in Compile).map { (opt,outDir) =>
            val path = outDir / "public" / "javascripts" / name
            if (!path.exists || path.lastModified < opt.lastModified) {
              IO.copyFile(opt, path, true)              
            }
            Seq[java.io.File](path)
          },
          playMonitoredFiles <<= (playMonitoredFiles, watchSources in ref).map { (files,refSources) =>
            (files ++ refSources.map(_.getCanonicalPath)).distinct
          }
        )
      }
  }
}