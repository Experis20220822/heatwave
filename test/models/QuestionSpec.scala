/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package models

import org.junit.Before
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class QuestionSpec extends AnyFreeSpec with Matchers {
  "Question" - {
    @Before
    val result = Question("anId", "name", "email@domain.com", "question")
    "id must contain a non empty string" in {
        result.id mustEqual("anId")
    }
    "email must contain a non empty string" in {
      result.email mustEqual("email@domain.com")
    }
    "username must contain a non empty string" in {
      result.name mustEqual ("name")
    }
    "password must contain a non empty string" in {
      result.question mustEqual ("question")
    }
  }
}
