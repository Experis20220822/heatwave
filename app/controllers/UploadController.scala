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

  case class uploadData(image: String, description: String)

  val formForUpload: Form[uploadData] = Form(
    mapping("image" -> text, "description" -> text)(uploadData.apply)(uploadData.unapply)
  )

  def index(): Action[AnyContent] = Action { implicit request =>
    Ok(view(formForUpload))
  }

  def fileUploaded(image: String, description: String): Action[AnyContent] = Action { implicit request =>
    Ok(nextPage(formForUpload, if (image.isEmpty) "http://www.macedonrangeshalls.com.au/wp-content/uploads/2017/10/image-not-found.png" else image, if (description.isEmpty) "No comment left." else description))
  }

  def submit(mode: Mode): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    formForUpload.bindFromRequest.fold(
      formWithErrors => {
        Future(BadRequest(view(formWithErrors)))
      },
      fileData => {
        val insertedId = uploadService.addFile(Upload("", fileData.image, fileData.description))
        val result = insertedId.map {
          case Some(str) => Redirect(routes.UploadController.success(str, fileData.image, fileData.description))
          case None => NotFound("")
        }
        result
      }
    )
  }

  def success(id: String, image: String, description: String): Action[AnyContent] = Action { implicit request =>
    Redirect(routes.UploadController.fileUploaded(image, description))
  }


}