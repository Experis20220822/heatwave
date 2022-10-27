/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package repositories

import models.User
import org.mongodb.scala.bson.BsonObjectId
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{Document, MongoDatabase}
import javax.inject.Inject
import scala.concurrent.Future

class UserRepository @Inject()(mongoDatabase: MongoDatabase) {
  val collection = mongoDatabase.getCollection("users")

  private def byId(id: String): Bson = Filters.equal("_id", BsonObjectId(id))

  private def byStringField(query: String, fieldName: String): Bson = Filters.equal(fieldName, query)

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

  def getByName(username: String): Future[Option[User]] = {
    collection.find(byStringField(username, "username"))
      .map(d => documentToUser(d)).toSingle().headOption()
  }

  def documentToUser(d: Document): User = {
    User(d("_id").asObjectId().getValue.toString, d("email").asString().getValue, d("username").asString().getValue, d("password").asString().getValue)
  }
}
