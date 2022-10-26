/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, User}

import scala.concurrent.Future

trait AsyncService {
    def add(user: User): Future[Option[String]]
    def add(invoice: Invoice): Future[Option[String]]
    def get(id: String): Future[Option[String]]
}
