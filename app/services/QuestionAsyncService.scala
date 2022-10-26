/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, Questions}

import scala.concurrent.Future

trait QuestionAsyncService {
  def add(question: Questions): Future[Option[String]]

  def getQuestion(id: String): Future[Option[Questions]]
}
