ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "triangle-paths",
    idePackagePrefix := Some("triangle.paths"),
    libraryDependencies ++= Seq(
      "com.monovore" %% "decline" % "2.4.1",
      "org.typelevel" %% "cats-effect" % "3.5.4",
      "org.typelevel" %% "cats-effect-kernel" % "3.5.4",
      "org.typelevel" %% "cats-effect-std" % "3.5.4",
      "org.typelevel" %% "cats-effect-testing-specs2" % "1.5.0" % Test,
      "org.typelevel" %% "munit-cats-effect" % "2.0.0" % Test,
      "co.fs2" %% "fs2-core" % "3.10.2",
      "co.fs2" %% "fs2-io" % "3.10.2"
    )
  )
