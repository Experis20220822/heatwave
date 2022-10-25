/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.Invoice

import scala.concurrent.Future

trait AsyncService {
    def add(invoice: Invoice): Future[Option[String]]
    def get(id: String): Future[Option[String]]
}
