/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Questions, Invoice, User}
import repositories.{QuestionRepository, InvoiceRepository, UserRepository}

import javax.inject.Inject
import scala.concurrent.Future

class QuestionService @Inject() (questionRepository: QuestionRepository) extends AsyncService {
  override def add(questions: Questions): Future[Option[String]] = questionRepository.add(questions)

  override def add(invoice: Invoice): Future[Option[String]] = ???

  override def get(id: String): Future[Option[String]] = ???

  override def add(user: User): Future[Option[String]] = ???
}
