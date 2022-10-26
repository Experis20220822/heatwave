/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, Questions, User}
import repositories.{InvoiceRepository, UserRepository}

import javax.inject.Inject
import scala.concurrent.Future

class InvoiceService @Inject() (invoiceRepository: InvoiceRepository) extends AsyncService {
  override def add(invoice: Invoice): Future[Option[String]] = invoiceRepository.add(invoice)

  override def get(id: String): Future[Option[String]] = ???

  override def add(user: User): Future[Option[String]] = ???

  override def add(questions: Questions): Future[Option[String]] = ???
}
