/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, User}
import repositories.UserRepository

import javax.inject.Inject
import scala.concurrent.Future

class UserService @Inject()(userRepository: UserRepository) extends UserAsyncService {
  override def addUser(user: User): Future[Option[String]] = userRepository.add(user)
}
