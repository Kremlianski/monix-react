lazy val root = project
  .enablePlugins(ScalaJSPlugin)

enablePlugins(WorkbenchPlugin)


name := "Example"

version := "0.2-SNAPSHOT"

scalaVersion := "2.12.8"

dependencyOverrides += "org.webjars.npm" % "js-tokens" % "3.0.2"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "io.monix" %%% "monix" % "2.3.3",
  "com.github.japgolly.scalajs-react" %%% "core" % "1.4.1"
)

jsDependencies ++= Seq(

  "org.webjars.npm" % "react" % "16.8.5"
    /        "umd/react.development.js"
    minified "umd/react.production.min.js"
    commonJSName "React",

  "org.webjars.npm" % "react-dom" % "16.8.5"
    /         "umd/react-dom.development.js"
    minified  "umd/react-dom.production.min.js"
    dependsOn "umd/react.development.js"
    commonJSName "ReactDOM"
)



