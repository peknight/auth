ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.7.1"

ThisBuild / organization := "com.peknight"

ThisBuild / publishTo := {
  val nexus = "https://nexus.peknight.com/repository"
  if (isSnapshot.value)
    Some("snapshot" at s"$nexus/maven-snapshots/")
  else
    Some("releases" at s"$nexus/maven-releases/")
}

ThisBuild / credentials ++= Seq(
  Credentials(Path.userHome / ".sbt" / ".credentials")
)

ThisBuild / resolvers ++= Seq(
  "Pek Nexus" at "https://nexus.peknight.com/repository/maven-public/",
)

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Xfatal-warnings",
    "-language:strictEquality",
    "-Xmax-inlines:64"
  ),
)

lazy val auth = (project in file("."))
  .aggregate(
    authCore.jvm,
    authCore.js,
    authHttp4s.jvm,
    authHttp4s.js,
  )
  .settings(commonSettings)
  .settings(
    name := "auth",
  )

lazy val authCore = (crossProject(JSPlatform, JVMPlatform) in file("auth-core"))
  .settings(commonSettings)
  .settings(
    name := "auth-core",
    libraryDependencies ++= Seq(
    ),
  )

lazy val authHttp4s = (crossProject(JSPlatform, JVMPlatform) in file("auth-http4s"))
  .dependsOn(
    authCore,
  )
  .settings(commonSettings)
  .settings(
    name := "auth-http4s",
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-core" % http4sVersion,
    ),
  )

val http4sVersion = "1.0.0-M34"
