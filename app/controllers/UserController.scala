/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import javax.inject._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

case class UserData(name: String, age: Int)
class UserController @Inject()(mcc: MessagesControllerComponents) extends MessagesAbstractController(mcc) {

  val userForm = Form(
    mapping(
      "name" -> text,
      "age" -> number
    )(UserData.apply)(UserData.unapply)
  )

  def userGet(): Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.user.form(userForm))
  }

  def userPost(): Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.user.form(formWithErrors))
      },
      userData => {
        Redirect(routes.UserController.userGet).flashing("success" -> "user.created")
      }
    )
  }
}
