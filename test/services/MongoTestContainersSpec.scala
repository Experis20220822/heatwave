/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import models.{Invoice, Question, User}
import org.mongodb.scala._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import repositories.{InvoiceRepository, QuestionRepository, UserRepository}
import com.dimafeng.testcontainers.{ForAllTestContainer, MongoDBContainer}

import scala.concurrent.ExecutionContext.Implicits.global

class MongoTestContainersSpec extends AnyFreeSpec with ForAllTestContainer {

  override val container: MongoDBContainer = new MongoDBContainer()
  val host: String = container.host

  "UserService" - {

    "create an user document and fetch the same document" in {
      val db: MongoDatabase = getDb
      val userService = new UserService(new UserRepository(db))
      val user = User("", "email", "username", "password")
      userService.addUser(user)

      val result = userService
        .getByName("username")
        .map {
          case Some(u) =>
            u.email mustEqual user.email
            u.username mustEqual user.username
            u.password mustEqual user.password
          case _ => ()
        }
      result
    }
  }

  "InvoiceService" - {

    "create an invoice document and fetch the same document" in {
      val db: MongoDatabase = getDb
      val invoiceService = new InvoiceService(new InvoiceRepository(db))
      val invoice = Invoice("63586003f780b223215b94e8", "customerDetails", "userDetails", "invoiceItem", 1, 2)
      invoiceService.add(invoice)

      val result = invoiceService
        .getInvoice("63586003f780b223215b94e8")
        .map {
          case Some(i) =>
            i.customerDetails mustEqual invoice.customerDetails
            i.userDetails mustEqual invoice.userDetails
            i.invoiceItem mustEqual invoice.invoiceItem
            i.invoiceItemPrice mustEqual invoice.invoiceItemPrice
            i.vatNumber mustEqual invoice.vatNumber
          case _ => ()
        }
      result
    }
  }

  "QuestionService" - {

    "create an question document and fetch the same document" in {
      val db: MongoDatabase = getDb
      val questionService = new QuestionService(new QuestionRepository(db))
      val question = Question("63586003f780b223215b94e8", "name", "email", "question")
      questionService.add(question)

      val result = questionService
        .getQuestion("63586003f780b223215b94e8")
        .map {
          case Some(q) =>
            q.name mustEqual question.name
            q.email mustEqual question.email
            q.question mustEqual question.question
          case _ => ()
        }
      result
    }
  }

  private def getDb = {
    val port: Int = container.livenessCheckPortNumbers.head
    val mongoClient: MongoClient = MongoClient(s"mongodb://$host:$port")
    val db = mongoClient.getDatabase("tests")
    db
  }
}
