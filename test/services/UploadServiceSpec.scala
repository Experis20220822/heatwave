/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import com.dimafeng.testcontainers.{ForAllTestContainer, MongoDBContainer}
import models.Upload
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import repositories.{UploadRepository, UserRepository}

import scala.concurrent.ExecutionContext.Implicits.global

class UploadServiceSpec
  extends AnyFreeSpec
    with ForAllTestContainer
    with ScalaCheckPropertyChecks { //Include ScalaCheckPropertyChecks is included so that we can perform property testing

  @Override
  val container: MongoDBContainer = new MongoDBContainer()

  "UploadService" - {

    "add an upload to the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("uploads")
      val myUpload = Upload("645b7bcc70660b7d096b7cfd", "image", "description")
      val uploadService = new UploadService(new UploadRepository(db))
      uploadService.addFile(myUpload)

      val result = collection.countDocuments().headOption()
      result.map{
        case Some(num) => num mustEqual 1
        case _ => ()
      }
      result
    }

    "retrieve an upload from the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("uploads")
      val myUpload = Upload("645b7bcc70660b7d096b7cfd", "image", "description")
      val document = Document(
        "image" -> myUpload.image,
        "description" -> myUpload.description
      )
      val userService = new UserService(new UserRepository(db))
      val getId = collection.insertOne(document).head()

      val maybeUser = getId.map(r => r.getInsertedId.asObjectId().getValue.toString).flatMap(id => userService.getUser(id))
      val result = maybeUser.map {
        case Some(user) => user mustEqual myUpload
        case _ => ()
      }
      result
    }
  }
}