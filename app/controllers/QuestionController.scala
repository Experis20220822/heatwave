/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import models.{Mode, Question}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, MessagesRequest}
import services.QuestionService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.question.question
import views.html.question.questioncreation.{QuestionCreationPage, questionconfirmation}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton class QuestionController @Inject()(val mcc: MessagesControllerComponents, view: question, creationView: QuestionCreationPage, questionService: QuestionService, confirmView: questionconfirmation)(implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  case class Data(name: String, email: String, question: String)

  val form: Form[Data] = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "question" -> nonEmptyText,
    )(Data.apply)(Data.unapply)
  )

  def index(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(view("Questions", "Heading", "SomeText"))
  }
  def confirmation(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(confirmView("Confirmation", "Heading", "SomeText"))
  }

  def creationIndex(mode: Mode): Action[AnyContent] = Action { implicit request =>
    Ok(creationView(form, mode))
  }

  def submit(mode: Mode): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    form.bindFromRequest.fold(
      formWithErrors => {
        Future(BadRequest(creationView(formWithErrors, mode)))
      },
      questionData => {
        val insertedId = questionService.add(Question("", questionData.name, questionData.email, questionData.question))
        val result = insertedId.map {
          case Some(str) => Redirect(routes.QuestionController.success(str))
          case None => NotFound("")
        }
        result
      }
    )
  }

  def success(id: String): Action[AnyContent] = Action { implicit request =>
    Redirect(routes.QuestionController.confirmation())
  }

}