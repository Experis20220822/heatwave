/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, User}
import org.mongodb.scala.Document
import repositories.{InvoiceRepository, UserRepository}

import javax.inject.Inject
import scala.concurrent.Future

class InvoiceService @Inject() (invoiceRepository: InvoiceRepository) extends AsyncService {
  override def add(invoice: Invoice): Future[Option[String]] = invoiceRepository.add(invoice)

  override def getInvoice(id: String): Future[Option[Invoice]] = invoiceRepository.get(id)

  override def addUser(user: User): Future[Option[String]] = ???
}
