/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import com.dimafeng.testcontainers.{ForAllTestContainer, MongoDBContainer}
import models.Invoice
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.{contain, convertToAnyMustWrapper}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import repositories.InvoiceRepository
import services.InvoiceService

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

object InvoiceServiceSpec {}

class InvoiceServiceSpec
  extends AnyFreeSpec
    with ForAllTestContainer
    with ScalaCheckPropertyChecks { //Include ScalaCheckPropertyChecks is included so that we can perform property testing

  @Override
  val container: MongoDBContainer = new MongoDBContainer()

  "InvoiceService" - {

    "add an invoice to the database and retrieve it" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("invoices")
      val invoice = Invoice("645b7bcc70660b7d096b7cfd", "customerDetails", "userDetails", "invoiceItem", 1 ,2)
      val document = Document(
        "customerDetails" -> invoice.customerDetails,
        "userDetails" -> invoice.userDetails,
        "invoiceItem" -> invoice.invoiceItem,
        "invoiceItemPrice" -> invoice.invoiceItemPrice,
        "vatNumber" -> invoice.vatNumber
      )
      val invoiceService = new InvoiceService(new InvoiceRepository(db))

      val getId = collection.insertOne(document).head()

      val maybeInvoice = getId.map(r => r.getInsertedId.asObjectId().getValue.toString).flatMap(id => invoiceService.getInvoice(id))

//      import scala.concurrent.duration._
//
//      val await: Option[Invoice] = Await.result(maybeInvoice, Duration.Inf)
//      await must contain(Some(document.toString()))
    }
  }
}