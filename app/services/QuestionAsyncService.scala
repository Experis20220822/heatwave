/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, Question}

import scala.concurrent.Future

trait QuestionAsyncService {
  def add(question: Question): Future[Option[String]]

  def getQuestion(id: String): Future[Option[Question]]
}
