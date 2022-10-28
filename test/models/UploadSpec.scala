/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package models

import org.junit.Before
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class UploadSpec extends AnyFreeSpec with Matchers {

  "Upload" - {
    @Before
    val result = Upload("anId", "imageStr")
    "id must contain a non empty string" in {
      result.id mustEqual("anId")
    }
    "email must contain a non empty string" in {
      result.image mustEqual("imageStr")
    }
  }
}

