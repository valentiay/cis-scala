name := "cis-scala"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.2.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.2.0"
libraryDependencies += "co.fs2" %% "fs2-core" % "2.4.4"
libraryDependencies += "co.fs2" %% "fs2-io" % "2.4.4"
libraryDependencies += "com.github.finagle" %% "finchx-core" % "0.32.1"
libraryDependencies += "io.circe" %% "circe-core" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-generic" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-generic-extras" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-parser" % "0.13.0"

scalacOptions += "-Ymacro-annotations"

addCompilerPlugin("org.wartremover" %% "wartremover" % "2.4.10" cross CrossVersion.full)

scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.AsInstanceOf"
scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.MutableDataStructures"
scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.Null"
scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.Return"
scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.Throw"
scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.Var"
scalacOptions += "-P:wartremover:traverser:org.wartremover.warts.While"