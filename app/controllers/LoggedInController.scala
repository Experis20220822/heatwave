/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.NormalMode
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.{LoggedInPage, text_input}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton class LoggedInController @Inject()(val mcc: MessagesControllerComponents, view: LoggedInPage, textInputView: text_input)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class Data(val field: String) {}

  val form: Form[Data] = Form[Data](
    mapping("field" -> text)(Data.apply)(Data.unapply)
  )

  def index(): Action[AnyContent] = Action { implicit request =>
    Ok(view(form, NormalMode))
  }
}
