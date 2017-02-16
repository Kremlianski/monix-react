lazy val root = project
  .enablePlugins(ScalaJSPlugin)

enablePlugins(WorkbenchPlugin)


name := "Example"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "io.monix" %%% "monix" % "2.1.1",
  "com.github.japgolly.scalajs-react" %%% "core" % "0.11.3"
)

jsDependencies ++= Seq(

  "org.webjars.bower" % "react" % "15.4.1"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "15.4.1"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM"
)



