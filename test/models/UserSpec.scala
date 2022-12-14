/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package models

import org.junit.Before
import org.mockito.{Mock, Mockito}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class UserSpec extends AnyFreeSpec with Matchers {
  "User" - {
    @Before
    val result = User("anId", "email@domain.com", "username", "password")
    "id must contain a non empty string" in {
        result.id mustEqual("anId")
    }
    "email must contain a non empty string" in {
      result.email mustEqual("email@domain.com")
    }
    "username must contain a non empty string" in {
      result.username mustEqual ("username")
    }
    "password must contain a non empty string" in {
      result.password mustEqual ("password")
    }
  }
}
