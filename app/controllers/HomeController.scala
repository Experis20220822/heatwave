/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{MessagesControllerComponents, Request}
import play.filters.csrf.CSRF
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.index


import scala.concurrent.{ExecutionContext, Future}


@Singleton class HomeController @Inject()(val mcc: MessagesControllerComponents, view: index) (implicit val executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {
def index() = Action(implicit request => Ok(view("Title", "Heading", "SomeText")))

}