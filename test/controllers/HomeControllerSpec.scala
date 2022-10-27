/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.mvc._
import play.api.i18n._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Play.materializer
import play.api.http.FileMimeTypes
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._
import views.html.{index, text_input}
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * User form controller specs
 */
class HomeControllerSpec extends AnyFreeSpec with GuiceOneAppPerTest with Injecting with Matchers {

  // Provide stubs for components based off Helpers.stubControllerComponents()
  class StubComponents(cc:ControllerComponents = stubControllerComponents()) extends MessagesControllerComponents {
    override val parsers: PlayBodyParsers = cc.parsers
    override val messagesApi: MessagesApi = cc.messagesApi
    override val langs: Langs = cc.langs
    override val fileMimeTypes: FileMimeTypes = cc.fileMimeTypes
    override val executionContext: ExecutionContext = cc.executionContext
    override val actionBuilder: ActionBuilder[Request, AnyContent] = cc.actionBuilder
    override val messagesActionBuilder: MessagesActionBuilder = new DefaultMessagesActionBuilderImpl(parsers.default, messagesApi)(executionContext)
  }

  "HomeController GET" - {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController(new StubComponents(), inject[index], inject[text_input])
      val request = FakeRequest().withCSRFToken
      val home = controller.index().apply(request)

      status(home) mustEqual OK
      contentType(home) mustEqual Some("text/html")
    }

    "render the index page from the application" in {
      val controller = inject[HomeController]
      val request = FakeRequest().withCSRFToken
      val home = controller.index().apply(request)

      status(home) mustEqual OK
      contentType(home) mustEqual Some("text/html")
    }
  }

//    "render the index page from the router" in {
//      val request = CSRFTokenHelper.addCSRFToken(FakeRequest(GET, "/"))
//      val home = route(app, request).get
//
//      status(home) mustEqual OK
//      contentType(home) mustEqual Some("text/html")
//    }
//  }

//  "UserController POST" should {
//    "process form" in {
//      val request = {
//        FakeRequest(POST, "/user")
//          .withFormUrlEncodedBody("name" -> "play", "age" -> "4")
//      }
//      val home = route(app, request).get
//
//      status(home) mustBe SEE_OTHER
//    }
//  }

}