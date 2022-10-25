package pages

import models.MultipleQuestionsPageName
import pages.behaviours.PageBehaviours

class MultipleQuestionsPageNamePageSpec extends PageBehaviours {

  "MultipleQuestionsPageNamePage" - {

    beRetrievable[MultipleQuestionsPageName](MultipleQuestionsPageNamePage)

    beSettable[MultipleQuestionsPageName](MultipleQuestionsPageNamePage)

    beRemovable[MultipleQuestionsPageName](MultipleQuestionsPageNamePage)
  }
}
