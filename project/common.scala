import sbt._

object Common {
  import Keys._

  val defaultScalaVersion = "2.11.6"

  val testSettings:Seq[Setting[_]] = Seq(
    testOptions in Test += Tests.Cleanup { loader =>
      val c = loader.loadClass("unfiltered.spec.Cleanup$")
      c.getMethod("cleanup").invoke(c.getField("MODULE$").get(c))
    }
  )

  val settings: Seq[Setting[_]] = ls.Plugin.lsSettings ++ Seq(
    version := "0.11.2-atlassian-1",

    crossScalaVersions := Seq("2.10.5", "2.11.6"),

    scalaVersion := defaultScalaVersion,

    organization := "net.databinder.dispatch",

    homepage :=
      Some(new java.net.URL("http://dispatch.databinder.net/")),

    publishMavenStyle := true,

    publishTo <<= version { (v: String) =>
      val nexus = "https://maven.atlassian.com/"
      if (v.trim.endsWith("SNAPSHOT")) 
        Some("snapshots" at nexus + "private-snapshot")
      else
        Some("releases"  at nexus + "private")
    },

    publishArtifact in Test := false,

    licenses := Seq("LGPL v3" -> url("http://www.gnu.org/licenses/lgpl.txt")),

    pomExtra := (
      <scm>
        <url>git@github.com:atlassian/reboot.git</url>
        <connection>scm:git:git@github.com:atlassian/reboot.git</connection>
      </scm>
      <developers>
        <developer>
          <id>n8han</id>
          <name>Nathan Hamblen</name>
          <url>http://twitter.com/n8han</url>
        </developer>
      </developers>)
  )
}
