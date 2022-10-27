/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.{Mode, Upload}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, MessagesRequest}
import services.UploadService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.upload.check.check
import views.html.upload.upload

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton class UploadController @Inject()(val mcc: MessagesControllerComponents, view: upload, nextPage: check, uploadService: UploadService)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class Data(image: String)

  val formForUpload: Form[Data] = Form[Data](
    mapping("image" -> text)(Data.apply)(Data.unapply)
  )

  def index(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(view(formForUpload, mode))
  }

  def fileUploaded(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(nextPage(formForUpload, mode, ""))
  }

  def submit(mode: Mode): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    formForUpload.bindFromRequest.fold(
      formWithErrors => {
        Future(BadRequest(view(formWithErrors, mode)))
      },
      fileData => {
        val insertedId = uploadService.addFile(Upload("", fileData.image))
        val result = insertedId.map {
          case Some(str) => Redirect(routes.UploadController.success(str))
          case None => NotFound("")
        }
        result
      }
    )
  }

  def success(id: String): Action[AnyContent] = Action { implicit request =>
    Redirect(routes.UploadController.fileUploaded())
  }


}