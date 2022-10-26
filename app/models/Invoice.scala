/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package models

case class Invoice(id: String, customerDetails: String, userDetails: String, invoiceItem: String, invoiceItemPrice: Int, vatNumber: Int)