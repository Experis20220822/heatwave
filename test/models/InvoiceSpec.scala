/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package models

import org.junit.Before
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class InvoiceSpec extends AnyFreeSpec with Matchers {
  "Invoice" - {
    @Before
    val result = Invoice("anId", "customerDetails", "userDetails", "invoiceItem", 1 ,2)
    "id must contain a non empty string" in {
      result.id mustEqual ("anId")
    }
    "customerDetails must contain a non empty string" in {
      result.customerDetails mustEqual ("customerDetails")
    }
    "userDetails must contain a non empty string" in {
      result.userDetails mustEqual ("userDetails")
    }
    "invoiceItem must contain a non empty string" in {
      result.invoiceItem mustEqual ("invoiceItem")
    }
    "invoiceItemPrice must contain an Int > 0" in {
      result.invoiceItemPrice mustEqual 1
    }
    "vatNumber must contain an Int > 0" in {
      result.vatNumber mustEqual 2
    }
  }
}
