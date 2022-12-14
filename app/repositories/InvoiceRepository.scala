/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package repositories
import models.Invoice
import org.mongodb.scala.bson.BsonObjectId
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{Document, MongoDatabase}

import javax.inject.Inject

class InvoiceRepository @Inject()(mongoDatabase: MongoDatabase) {
  val collection = mongoDatabase.getCollection("invoices")

  private def byId(id: String): Bson = Filters.equal("_id", id)

  def get(id: String) = {
    collection.find(equal("_id", BsonObjectId(id)))
      .map(d => documentToInvoice(d))
  }.toSingle().headOption()

  def add(i: Invoice) = {
    collection.insertOne(
      Document(
        "customerDetails" -> i.customerDetails,
        "userDetails" -> i.userDetails,
        "invoiceItem" -> i.invoiceItem,
        "invoiceItemPrice" -> i.invoiceItemPrice,
        "vatNumber" -> i.vatNumber,
      )
    ).map(r => r.getInsertedId.asObjectId().getValue.toString).headOption()
  }

  def documentToInvoice(d: Document): Invoice = {
    Invoice(d("_id").toString, d("customerDetails").asString().getValue, d("userDetails").asString().getValue, d("invoiceItem").asString().getValue,
      d("invoiceItemPrice").asInt32().getValue, d("vatNumber").asInt32().getValue)
  }
}