/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, Questions, User}
import repositories.UserRepository

import javax.inject.Inject
import scala.concurrent.Future

class UserService @Inject() (userRepository: UserRepository) extends AsyncService {
  override def get(id: String): Future[Option[String]] = ???

  override def add(user: User): Future[Option[String]] = userRepository.add(user)

  override def add(invoice: Invoice): Future[Option[String]] = ???

  override def add(questions: Questions): Future[Option[String]] = ???
}
