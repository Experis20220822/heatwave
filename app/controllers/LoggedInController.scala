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
import views.html.{InvoicePage, text_input}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext


@Singleton class LoggedInController @Inject()(val mcc: MessagesControllerComponents, view: InvoicePage, textInputView: text_input)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

}
