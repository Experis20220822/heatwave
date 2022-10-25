/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, User}
import repositories.{InvoiceRepository, UserRepository}

import javax.inject.Inject
import scala.concurrent.Future

class InvoiceService @Inject() (invoiceRepository: InvoiceRepository) extends AsyncService {
  override def add(invoice: Invoice) = invoiceRepository.add(invoice)

  override def get(id: String): Future[Option[String]] = ???
}
