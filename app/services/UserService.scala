/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.User
import repositories.UserRepository

import javax.inject.Inject

class UserService @Inject() (userRepository: UserRepository){
  def addUser(user: User) = userRepository.addUser(user)
}
