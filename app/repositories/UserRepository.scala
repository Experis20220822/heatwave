/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package repositories

import models.User
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{Document, MongoDatabase}
import org.mongodb.scala.model.Filters.equal

import javax.inject.Inject

class UserRepository @Inject()(mongoDatabase: MongoDatabase) {
  val collection = mongoDatabase.getCollection("users")

  private def byId(id: String): Bson = Filters.equal("_id", id)

  def getUser(id: String) = {
    collection.find(byId(id))
      .map(d => documentToUser(d)).toSingle().headOption()
  }

  def addUser(u: User) = {
    collection.insertOne(
      Document(
        u.email -> "email",
        u.username -> "username",
        u.password -> "password"
      )
    )
      .subscribe(r => println(s"Successful insert: $r"), t => t.printStackTrace(), () => println("Insert Complete"))
  }

  def documentToUser(d: Document): User = {
    User(d("_id").toString, d("email").toString, d("username").toString, d("password").toString)
  }
}
