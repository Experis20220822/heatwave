/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Upload, Question, Invoice, User}
import repositories.UploadRepository

import javax.inject.Inject
import scala.concurrent.Future

class UploadService @Inject()(uploadRepository: UploadRepository) extends UploadAsyncService {
  override def addFile(image: Upload): Future[Option[String]] = uploadRepository.add(image)

  override def getFile(id: String): Future[Option[Upload]] = uploadRepository.get(id)

  override def getByFileName(image: String): Future[Option[Upload]] = uploadRepository.getByFileName(image)
}
