/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.Mode
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, text}

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, MessagesRequest, Request}
import play.filters.csrf.CSRF
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.form

import scala.concurrent.{ExecutionContext, Future}


@Singleton class RegisterController @Inject()(val mcc: MessagesControllerComponents, view: form)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {
  case class UserData(email: String, username: String, password: String)

  val userRegistration = Form[UserData](
    mapping(
      "email" -> nonEmptyText,
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(UserData.apply)(UserData.unapply)
  )

  def index(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(view(userRegistration, mode))
  }

  def submit(mode: Mode): Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    userRegistration.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(view(formWithErrors, mode))
      },
      userData => {
        Redirect(routes.HomeController.index()).flashing("success" -> "user.created")
      }
    )
  }
}