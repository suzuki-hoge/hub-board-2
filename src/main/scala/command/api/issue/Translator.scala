package command.api.issue

import command.domain.issue._
import ujson.Obj

object Translator {
  def errorToJson(s: String): String = Obj("error" -> s).render()
  def issueToJson(issue: Issue): String = Obj("number" -> issue.n.v, "title" -> issue.orgT.v).render()
}
