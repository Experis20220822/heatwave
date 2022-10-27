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

  override def getUser(id: String): Future[Option[User]] = userRepository.get(id)

  override def getByName(name: String): Future[Option[User]] = userRepository.getByName(name)
}
