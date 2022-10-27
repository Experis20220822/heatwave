/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.Mode
import play.api.data.Form
import play.api.data.Forms.{mapping, text}

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import play.filters.csrf.CSRF
import services.UserService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.login.login
import views.html.logged.LoggedInPage

import scala.concurrent.{ExecutionContext, Future}


@Singleton class LoginController @Inject()(val mcc: MessagesControllerComponents, view: login, userService: UserService, loggedInView: LoggedInPage)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class UserLoginData(username: String, password: String)

  val userLogin: Form[UserLoginData] = Form[UserLoginData](
    mapping(
      "username" -> text,
      "password" -> text)
    (UserLoginData.apply)(UserLoginData.unapply)
  )

  def index(): Action[AnyContent] = Action { implicit request =>
    Ok(view(userLogin))
  }

  def login(): Action[AnyContent] = Action.async { implicit request =>
    userLogin.bindFromRequest().fold(
      formWithErrors => Future(BadRequest(view(formWithErrors))),
      formData => {
        val maybeUser = userService.getByName(formData.username)
        maybeUser.map {
          case Some(user) => if (user.password == formData.password) Ok(loggedInView(userLogin, user.username)) else NotFound("Password did not match.")
          case None => NotFound("This username was not found, please try again.")
        }
      }
    )
  }
}