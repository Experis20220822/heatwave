/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package viewmodels

sealed trait LegendSize

object LegendSize {
  case object ExtraLarge extends WithCssClass("govuk-fieldset__legend--xl") with LegendSize
  case object Large      extends WithCssClass("govuk-fieldset__legend--l") with LegendSize
  case object Medium     extends WithCssClass("govuk-fieldset__legend--m") with LegendSize
  case object Small      extends WithCssClass("govuk-fieldset__legend--s") with LegendSize
}
