/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import com.typesafe.config.ConfigFactory
import models.NormalMode
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.FileMimeTypes
import play.api.i18n.{Langs, MessagesApi}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout, stubControllerComponents}
import play.api.{Application, Configuration}

import scala.concurrent.{ExecutionContext, Future}

class QuestionControllerSpec extends PlaySpec with GuiceOneAppPerTest with Results{
  class StubComponents(cc: ControllerComponents = stubControllerComponents()) extends MessagesControllerComponents {
    override val parsers: PlayBodyParsers = cc.parsers
    override val messagesApi: MessagesApi = cc.messagesApi
    override val langs: Langs = cc.langs
    override val fileMimeTypes: FileMimeTypes = cc.fileMimeTypes
    override val executionContext: ExecutionContext = cc.executionContext
    override val actionBuilder: ActionBuilder[Request, AnyContent] = cc.actionBuilder
    override val messagesActionBuilder: MessagesActionBuilder = new DefaultMessagesActionBuilderImpl(parsers.default, messagesApi)(executionContext)
  }

  override def fakeApplication(): Application = {
    GuiceApplicationBuilder().configure(Configuration(ConfigFactory.load("application.conf"))).build()
  }

  "Invoice creation page" should {
    "render the service index" in {
      val controller = app.injector.instanceOf(classOf[QuestionController])
      val result: Future[Result] = controller.index(NormalMode).apply(FakeRequest())
      contentAsString(result) must include("Ask us a Question")
    }
  }
}
