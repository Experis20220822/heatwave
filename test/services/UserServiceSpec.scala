/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import com.dimafeng.testcontainers.{ForAllTestContainer, MongoDBContainer}
import models.{Invoice, User}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import repositories.{InvoiceRepository, UserRepository}

import scala.concurrent.ExecutionContext.Implicits.global

class UserServiceSpec
  extends AnyFreeSpec
    with ForAllTestContainer
    with ScalaCheckPropertyChecks { //Include ScalaCheckPropertyChecks is included so that we can perform property testing

  @Override
  val container: MongoDBContainer = new MongoDBContainer()

  "UserService" - {

    "add an user to the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("users")
      val myUser = User("645b7bcc70660b7d096b7cfd", "email", "username", "password")
      val userService = new UserService(new UserRepository(db))
      userService.addUser(myUser)

      val result = collection.countDocuments().headOption()
      result.map{
        case Some(num) => num mustEqual 1
        case _ => ()
      }
      result
    }

    "retrieve an user from the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("invoices")
      val myUser = User("645b7bcc70660b7d096b7cfd", "email", "username", "password")
      val document = Document(
        "email" -> myUser.email,
        "userDetails" -> myUser.username,
        "invoiceItem" -> myUser.password
      )
      val userService = new UserService(new UserRepository(db))
      val getId = collection.insertOne(document).head()

      val maybeUser = getId.map(r => r.getInsertedId.asObjectId().getValue.toString).flatMap(id => userService.getUser(id))
      val result = maybeUser.map {
        case Some(user) => user mustEqual myUser
        case _ => ()
      }
      result
    }
  }
}