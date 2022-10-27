/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package repositories

import models.Upload
import org.mongodb.scala.bson.BsonObjectId
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{Document, MongoDatabase}

import javax.inject.Inject

class UploadRepository @Inject()(mongoDatabase: MongoDatabase) {
  val collection = mongoDatabase.getCollection("file_uploads")

  private def byId(id: String): Bson = Filters.equal("_id", BsonObjectId(id))

  def get(id: String) = {
    collection.find(byId(id))
      .map(d => documentToUser(d)).toSingle().headOption()
  }

  def getByFileName(image: String) = {
    collection.find(Filters.equal("image", image))
      .map(d => documentToUser(d)).toSingle().headOption()
  }

  def add(u: Upload) = {
    collection.insertOne(
      Document(
        "image" -> u.image,
      )
    ).map(r => r.getInsertedId.asObjectId().getValue.toString).headOption()
  }

  def documentToUser(d: Document): Upload = {
    Upload(d("_id").asObjectId().getValue.toString, d("image").asString().getValue)
  }
}
