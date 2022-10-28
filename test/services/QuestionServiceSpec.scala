/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import com.dimafeng.testcontainers.{ForAllTestContainer, MongoDBContainer}
import models.Question
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import repositories.QuestionRepository

import scala.concurrent.ExecutionContext.Implicits.global

class QuestionServiceSpec
  extends AnyFreeSpec
    with ForAllTestContainer
    with ScalaCheckPropertyChecks { //Include ScalaCheckPropertyChecks is included so that we can perform property testing

  @Override
  val container: MongoDBContainer = new MongoDBContainer()

  "QuestionService" - {

    "add a question to the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("questions")
      val myQuestion = Question("645b7bcc70660b7d096b7cfd", "name", "email", "question")
      val questionService = new QuestionService(new QuestionRepository(db))
      questionService.add(myQuestion)

      val result = collection.countDocuments().headOption()
      result.map{
        case Some(num) => num mustEqual 1
        case _ => ()
      }
      result
    }

    "retrieve a question from the database" in {
      val client: MongoClient = MongoClient(container.container.getConnectionString)
      val db: MongoDatabase = client.getDatabase("heatwave")
      val collection: MongoCollection[Document] = db.getCollection("questions")
      val myQuestion = Question("645b7bcc70660b7d096b7cfd", "name", "email", "question")
      val document = Document(
        "name" -> myQuestion.name,
        "email" -> myQuestion.email,
        "question" -> myQuestion.question
      )
      val questionService = new QuestionService(new QuestionRepository(db))
      val getId = collection.insertOne(document).head()

      val maybeUser = getId.map(r => r.getInsertedId.asObjectId().getValue.toString).flatMap(id => questionService.getQuestion(id))
      val result = maybeUser.map {
        case Some(question) => question mustEqual myQuestion
        case _ => ()
      }
      result
    }
  }
}