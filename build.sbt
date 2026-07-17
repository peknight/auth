import com.peknight.build.gav.*
import com.peknight.build.sbt.*

commonSettings

lazy val auth = rootProject
  .settings(name := "auth")
  .settings(publish / skip := true)
  .aggregate(authCore.projectRefs *)
  .aggregate(authHttp4s.projectRefs *)

lazy val authCore = (projectMatrix in file("auth-core"))
  .settings(name := "auth-core")
  .settings(libraryDependencies ++= dependencies(
    peknight.codec.effect,
  ))
  .jvmPlatform(scalaVersions = Seq(scala.scala3.version))
  .jsPlatform(scalaVersions = Seq(scala.scala3.version))

lazy val authHttp4s = (projectMatrix in file("auth-http4s"))
  .dependsOn(authCore)
  .settings(name := "auth-http4s")
  .settings(libraryDependencies ++= dependencies(http4s))
  .jvmPlatform(scalaVersions = Seq(scala.scala3.version))
  .jsPlatform(scalaVersions = Seq(scala.scala3.version))
