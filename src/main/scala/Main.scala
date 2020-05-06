import boot.{Config, Store}
import cask.{MainRoutes, Request, post}
import command.api.issue.CommandIssueApi

object Main extends MainRoutes {

  Config.initialize("/tmp")

  @post("/command/issue/create")
  def create(request: Request): String = CommandIssueApi.create(request, Store().ps, Store().cm)

  initialize()
}
