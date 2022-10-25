package controllers

import play.api.mvc._
import play.api.i18n._
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.FileMimeTypes
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._

import scala.concurrent.ExecutionContext

/**
 * User form controller specs
 */
class UserControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

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

  "UserController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new UserController(new StubComponents())
      val request = FakeRequest().withCSRFToken
      val home = controller.userGet().apply(request)

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
    }

    "render the index page from the application" in {
      val controller = inject[UserController]
      val request = FakeRequest().withCSRFToken
      val home = controller.userGet().apply(request)

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
    }

    "render the index page from the router" in {
      val request = CSRFTokenHelper.addCSRFToken(FakeRequest(GET, "/user"))
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
    }
  }

  "UserController POST" should {
    "process form" in {
      val request = {
        FakeRequest(POST, "/user")
          .withFormUrlEncodedBody("name" -> "play", "age" -> "4")
      }
      val home = route(app, request).get

      status(home) mustBe SEE_OTHER
    }
  }

}
