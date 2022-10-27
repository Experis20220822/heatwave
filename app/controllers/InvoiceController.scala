/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.{Invoice, Mode, NormalMode}
import play.api.data._
import play.api.data.Forms.{bigDecimal, longNumber, mapping, nonEmptyText, number, text}
import play.api.data.validation.{Constraint, Invalid, Valid}

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, MessagesRequest, Request}
import play.filters.csrf.CSRF
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.text_input
import views.html.invoice._
import services.InvoiceService

import scala.concurrent.{ExecutionContext, Future}


@Singleton class InvoiceController @Inject()(val mcc: MessagesControllerComponents, view: InvoicePage, invoiceService: InvoiceService)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class Data(
                   customerDetails: String,
                   userDetails: String,
                   invoiceItem: String,
                   invoiceItemPrice: BigDecimal,
                   vatNumber: Int
                 )

//  val precisionOf2dp: Constraint[BigDecimal] = Constraint[BigDecimal] {
//    case d: BigDecimal if d.precision != 2 => Valid
//    case _ => Invalid("Must be a negative number.")
//  }

  val form: Form[Data] = Form[Data](
    mapping(
      "customerDetails" -> nonEmptyText,
      "userDetails" -> nonEmptyText,
      "invoiceItem" -> nonEmptyText,
      "invoiceItemPrice" -> bigDecimal,
      "vatNumber" -> number
    )(Data.apply)(Data.unapply)
  )

  def index(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(view(form, mode))
  }

  def submit(mode: Mode): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    form.bindFromRequest.fold(
      formWithErrors => {
        Future(BadRequest(view(formWithErrors, mode)))
      },
      invoiceData => {
        val newInvoiceData: Invoice = Invoice(
          "",
          invoiceData.customerDetails,
          invoiceData.userDetails,
          invoiceData.invoiceItem,
          (invoiceData.invoiceItemPrice*100).toInt,
          invoiceData.vatNumber
        )
        val maybeIdString = invoiceService.add(newInvoiceData)
        val result = maybeIdString.map {
          case Some(str) => Redirect(routes.InvoiceController.success(str))
          case None => NotFound("")
        }
        result
      }
    )
  }

  def success(id: String): Action[AnyContent] = Action.async { implicit request =>
    invoiceService
      .getInvoice(id)
      .map {
        case Some(invoice) => Ok(views.html.invoice.invoiceGenerated(invoice))
        case None => NotFound("Invoice not found")
      }
  }
}
