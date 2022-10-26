/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package repositories
import models.{Questions, Invoice, User}
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{Document, MongoDatabase}

import javax.inject.Inject
import scala.concurrent.Future

class QuestionRepository @Inject()(mongoDatabase: MongoDatabase) {
  val collection = mongoDatabase.getCollection("questions")

  private def byId(id: String): Bson = Filters.equal("_id", id)

  def get(id: String): Future[Option[Questions]] = {
    collection.find(byId(id))
      .map(d => documentToQuestions(d)).toSingle().headOption()
  }

  def add(i: Questions): Future[Option[String]] = {
    collection.insertOne(
      Document(
        "name" -> i.name,
        "email" -> i.email,
        "question" -> i.question
      )
    ).map(r => r.getInsertedId.asObjectId().getValue.toString).headOption()
  }

  def documentToQuestions(d: Document): Questions = {
    Questions(d("_id").toString, d("name").toString, d("email").toString, d("question").toString)
  }
}