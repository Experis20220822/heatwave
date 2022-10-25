/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.{Mode, NormalMode}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number, text}

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import play.filters.csrf.CSRF
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.{InvoicePage, text_input}

import scala.concurrent.{ExecutionContext, Future}



@Singleton class InvoiceController @Inject()(val mcc: MessagesControllerComponents, view: InvoicePage, textInputView: text_input)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class Data(
                  customerDetails: String,
                  userDetails: String,
                  invoiceItem: String,
                  invoiceItemPrice: Int,
                  vatNumber: Int
                 ) {}

  val form: Form[Data] = Form[Data](
    mapping(
      "customerDetails" -> nonEmptyText,
      "userDetails" -> nonEmptyText,
      "invoiceItem" -> nonEmptyText,
      "invoiceItemPrice" -> number(min = 1),
      "vatNumber" -> number
    )(Data.apply)(Data.unapply)
  )

  def index(): Action[AnyContent] = Action { implicit request =>
    Ok(view(form, NormalMode))
  }
}
