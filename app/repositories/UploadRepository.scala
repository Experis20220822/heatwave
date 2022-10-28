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
import scala.concurrent.Future

class UploadRepository @Inject()(mongoDatabase: MongoDatabase) {
  val collection = mongoDatabase.getCollection("uploads")

  private def byId(id: String): Bson = Filters.equal("_id", BsonObjectId(id))

  def get(id: String): Future[Option[Upload]] = {
    collection.find(byId(id))
      .map(d => documentToUpload(d)).toSingle().headOption()
  }

  def getByFileName(image: String): Future[Option[Upload]] = {
    collection.find(Filters.equal("image", image))
      .map(d => documentToUpload(d)).toSingle().headOption()
  }

  def getDescription(description: String): Future[Option[Upload]] = {
    collection.find(Filters.equal("description", description))
      .map(d => documentToUpload(d)).toSingle().headOption()
  }

  def add(u: Upload): Future[Option[String]] = {
    collection.insertOne(
      Document(
        "image" -> u.image,
        "description" -> u.description
      )
    ).map(r => r.getInsertedId.asObjectId().getValue.toString).headOption()
  }

  def documentToUpload(d: Document): Upload = {
    Upload(d("_id").asObjectId().getValue.toString, d("image").asString().getValue, d("description").asString().getValue
    )
  }
}
