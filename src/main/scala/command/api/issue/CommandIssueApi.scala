package command.api.issue

import boot.domain.Store
import cask.Request
import command.api.issue.Translator.{errorToJson, issueToJson}
import command.api.issue.Validator._
import command.domain.issue._
import command.domain.milestone.CurrentMilestoneName
import command.transfer.domain.issue.IssueRepositoryImpl

object CommandIssueApi {
  private val repo = IssueRepositoryImpl

  def create(request: Request, store: Store): String = {
    val command = request.text().split("&").toSeq.filter(_.startsWith("command")).map(_.split("=")(1)).headOption.getOrElse("")

    def validate(s: String, cm: CurrentMilestoneName): Either[String, CreateRequest] = for {
      _ <- formatV(s, "issue create"); t <- tV(s); b <- bV(s); ls <- lsV(s); as <- asV(s); p <- pV(s); m <- mV(s); e <- eV(s)
    } yield CreateRequest.of(t, b, ls, as, p, m, e, cm)

    validate(command, store.cm)
      .map(repo.create)
      .fold(errorToJson, issueToJson)
  }
}
