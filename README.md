This is a minimal example of scalajs integrated with play, supporting:

  * Incremental Compilation of Scala.js on page refresh
  * Compilation errors for scala.js displayed in the browser
  * Multiple scala.js projects can be added to a play project

Scala.js projects are untouched. To wire the project you simply call

    .dependsOnJs(scalaJsProjectA -> "jsfileA.js",
                 scalaJsProjectB -> "jsfileB.js")              

on the play project. The `dependsOnJs` function is introduced by an implicit class in
the ScalaJSPlay "plugin" (at `project/ScalaJSPlay.scala`)