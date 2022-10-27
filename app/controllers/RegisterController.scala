/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.{Mode, User}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, MessagesRequest}
import services.UserService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.user.form

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton class RegisterController @Inject()(val mcc: MessagesControllerComponents, view: form, userService: UserService)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {
  case class UserData(email: String, username: String, password: String)

  val userRegistration: Form[UserData] = Form(
    mapping(
      "email" -> nonEmptyText,
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(UserData.apply)(UserData.unapply)
  )

  def index(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(view(userRegistration, mode))
  }

  def submit(mode: Mode): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    userRegistration.bindFromRequest.fold(
      formWithErrors => {
        Future(BadRequest(view(formWithErrors, mode)))
      },
      userData => {
        val insertedId = userService.addUser(User("", userData.email, userData.username, userData.password))
        val result = insertedId.map {
          case Some(str) => Redirect(routes.RegisterController.success(str))
          case None => NotFound("")
        }
        result
      }
    )
  }

  def success(id: String): Action[AnyContent] = Action { implicit request =>
    Redirect(routes.LoginController.login())
  }

}