

lazy val root = project
  .in(file("."))
  .settings(
    name := "mirror_test",
    version := "0.1.0",

    scalaVersion := dottyLatestNightlyBuild.get,

    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "ch.epfl.lamp" % "dotty_0.22" % (scalaVersion.value)
    )
  )
