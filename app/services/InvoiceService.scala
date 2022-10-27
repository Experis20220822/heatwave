/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.Invoice
import repositories.InvoiceRepository

import javax.inject.Inject
import scala.concurrent.Future

class InvoiceService @Inject()(invoiceRepository: InvoiceRepository) extends InvoiceAsyncService {
  override def add(invoice: Invoice): Future[Option[String]] = invoiceRepository.add(invoice)

  override def getInvoice(id: String): Future[Option[Invoice]] = invoiceRepository.get(id)
}
