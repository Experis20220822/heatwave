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

    "add an invoice to the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("invoices")
      val myInvoice = Invoice("645b7bcc70660b7d096b7cfd", "customerDetails", "userDetails", "invoiceItem", 1 ,2)
      val document = Document(
        "customerDetails" -> myInvoice.customerDetails,
        "userDetails" -> myInvoice.userDetails,
        "invoiceItem" -> myInvoice.invoiceItem,
        "invoiceItemPrice" -> myInvoice.invoiceItemPrice,
        "vatNumber" -> myInvoice.vatNumber
      )
      val invoiceService = new InvoiceService(new InvoiceRepository(db))

      invoiceService.add(myInvoice)

      val result = collection.countDocuments().headOption()

      result.map{
        case Some(num) => num mustEqual 1
        case _ => ()
      }
      result
    }

    "retrieve an invoice from the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("invoices")
      val myInvoice = Invoice("645b7bcc70660b7d096b7cfd", "customerDetails", "userDetails", "invoiceItem", 1, 2)
      val document = Document(
        "customerDetails" -> myInvoice.customerDetails,
        "userDetails" -> myInvoice.userDetails,
        "invoiceItem" -> myInvoice.invoiceItem,
        "invoiceItemPrice" -> myInvoice.invoiceItemPrice,
        "vatNumber" -> myInvoice.vatNumber
      )
      val invoiceService = new InvoiceService(new InvoiceRepository(db))

      val getId = collection.insertOne(document).head()

      val maybeInvoice = getId.map(r => r.getInsertedId.asObjectId().getValue.toString).flatMap(id => invoiceService.getInvoice(id))
      val result = maybeInvoice.map {
        case Some(invoice) => invoice mustEqual myInvoice
        case _ => ()
      }
      result
    }
  }

  def documentToInvoice(d: Document): Invoice = {
    Invoice(d("_id").toString, d("customerDetails").asString().getValue, d("userDetails").asString().getValue, d("invoiceItem").asString().getValue,
      d("invoiceItemPrice").asInt32().getValue, d("vatNumber").asInt32().getValue)
  }
}