/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package repositories

import models.User
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{Document, MongoDatabase}
import javax.inject.Inject

class UserRepository @Inject()(mongoDatabase: MongoDatabase) {
  val collection = mongoDatabase.getCollection("users")

  private def byId(id: String): Bson = Filters.equal("_id", id)

  def get(id: String) = {
    collection.find(byId(id))
      .map(d => documentToUser(d)).toSingle().headOption()
  }

  def add(u: User) = {
    collection.insertOne(
      Document(
        "email" -> u.email,
        "username" -> u.username,
        "password" -> u.password
      )
    ).map(r => r.getInsertedId.asObjectId().getValue.toString).headOption()
  }

  def documentToUser(d: Document): User = {
    User(d("_id").toString, d("email").toString, d("username").toString, d("password").toString)
  }
}
