/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Questions, Invoice, User}
import repositories.{QuestionRepository, InvoiceRepository, UserRepository}

import javax.inject.Inject
import scala.concurrent.Future

class QuestionService @Inject() (questionRepository: QuestionRepository) extends QuestionAsyncService {

  override def getQuestion(id: String): Future[Option[Questions]] = questionRepository.get(id)

  override def add(question: Questions): Future[Option[String]] = questionRepository.add(question)
}
