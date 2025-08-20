import com.peknight.build.gav.*
import com.peknight.build.sbt.*

commonSettings

lazy val auth = (project in file("."))
  .settings(name := "auth")
  .aggregate(
    authCore.jvm,
    authCore.js,
    authCore.native,
    authHttp4s.jvm,
    authHttp4s.js,
  )

lazy val authCore = (crossProject(JVMPlatform, JSPlatform, NativePlatform) in file("auth-core"))
  .settings(name := "auth-core")

lazy val authHttp4s = (crossProject(JVMPlatform, JSPlatform) in file("auth-http4s"))
  .dependsOn(authCore)
  .settings(name := "auth-http4s")
  .settings(crossDependencies(http4s))
