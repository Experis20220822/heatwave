/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, User}

import scala.concurrent.Future

trait UserAsyncService {
  def addUser(user: User): Future[Option[String]]

  def getUser(id: String): Future[Option[User]]
}
