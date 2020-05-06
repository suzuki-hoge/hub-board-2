package command.api.issue

import cask.Request
import command.api.issue.Translator.{errorToJson, issueToJson}
import command.api.issue.Validator._
import command.domain.issue._
import command.domain.milestone.CurrentMilestoneName
import command.transfer.domain.issue.IssueRepositoryImpl

object CommandIssueApi {
  private val repo = IssueRepositoryImpl

  def create(request: Request, ps: Seq[Pipeline], cm: CurrentMilestoneName): String = {
    val command = request.text().split("&").toSeq.filter(_.startsWith("command")).map(_.split("=")(1)).headOption.getOrElse("")

    def validate(s: String, cm: CurrentMilestoneName): Either[String, CreateRequest] = for {
      _ <- formatV(s, "issue create"); t <- tV(s); ob <- bV(s); ls <- lsV(s); as <- asV(s); op <- pV(s); om <- mV(s); oe <- eV(s)
    } yield CreateRequest.of(t, ob, ls, as, op.flatMap(p => ps.find(_.name == p).map(_.id)), om, oe, cm)

    validate(command, cm)
      .map(repo.create)
      .fold(errorToJson, issueToJson)
  }
}
