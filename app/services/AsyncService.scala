/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, User}

import scala.concurrent.Future

trait AsyncService {
    def addUser(user: User): Future[Option[String]]
    def add(invoice: Invoice): Future[Option[String]]
    def getInvoice(id: String): Future[Option[Invoice]]
}
