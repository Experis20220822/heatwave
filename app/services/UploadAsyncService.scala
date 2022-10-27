/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Upload, Question, Invoice, User}

import scala.concurrent.Future

trait UploadAsyncService {
  def addFile(image: Upload): Future[Option[String]]

  def getFile(id: String): Future[Option[Upload]]

  def getByFileName(image: String): Future[Option[Upload]]
}
