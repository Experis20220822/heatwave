import sbt._

object AppDependencies {
  import play.core.PlayVersion

  val testContainersScalaVersion = "0.40.11"

  val compile: Seq[ModuleID] = Seq(
    play.sbt.PlayImport.ws,
    "uk.gov.hmrc"       %% "play-frontend-hmrc"            % "3.23.0-play-28",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-28"    % "7.1.0",
    "uk.gov.hmrc"       %% "play-language"                 % "5.1.0-play-28",
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-28"            % "0.71.0",
    "org.julienrf"      %% "play-json-derived-codecs"      % "10.0.2",
    "org.typelevel"     %% "cats-core"                     % "2.7.0",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping" % "1.11.0-play-28",
    "com.openhtmltopdf"  % "openhtmltopdf-pdfbox"          % "1.0.10",
    "com.google.inject"  % "guice"                         % "5.1.0"
  )

  val test: Seq[ModuleID] = Seq(
    "org.scalatest"          %% "scalatest"               % "3.2.10",
    "org.scalatestplus"      %% "scalacheck-1-15"         % "3.2.10.0",
    "org.scalatestplus"      %% "mockito-3-4"             % "3.2.10.0",
    "org.scalatestplus.play" %% "scalatestplus-play"      % "5.1.0",
    "com.typesafe.play"      %% "play-test"               % PlayVersion.current,
    "org.mockito"            %% "mockito-scala"           % "1.16.42",
    "org.scalacheck"         %% "scalacheck"              % "1.15.4",
    "com.vladsch.flexmark"    % "flexmark-all"            % "0.62.2",
    "com.softwaremill.diffx" %% "diffx-scalatest-should"  % "0.7.1",
    "com.dimafeng"           %% "testcontainers-scala-scalatest" % testContainersScalaVersion,
    "com.dimafeng"           %% "testcontainers-scala-mongodb"   % testContainersScalaVersion
  ).map(_ % "test, it")

  def apply(): Seq[ModuleID] = compile ++ test
}
