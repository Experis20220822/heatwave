/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.Mode

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views.html.form

//case class UserData(username: String, password: String)
//class UserController @Inject()(mcc: MessagesControllerComponents, view: form) extends MessagesAbstractController(mcc) {
//
//  val userForm = Form(
//    mapping(
//      "name" -> text,
//      "age" -> text
//    )(UserData.apply)(UserData.unapply)
//  )
//
//  def userGet(mode: Mode): Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
//    Ok(view(userForm, mode))
//  }
//
//  def userPost(mode: Mode): Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
//    userForm.bindFromRequest.fold(
//      formWithErrors => {
//        BadRequest(view(formWithErrors, mode))
//      },
//      userData => {
//        Redirect(routes.UserController.userGet).flashing("success" -> "user.created")
//      }
//    )
//  }
//}
