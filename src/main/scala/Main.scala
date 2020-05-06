import boot.transfer.StoreRepositoryImpl
import cask.{MainRoutes, Request, post}
import command.api.issue.CommandIssueApi

object Main extends MainRoutes {

  private val store = StoreRepositoryImpl.initialize

  @post("/command/issue/create")
  def create(request: Request): String = CommandIssueApi.create(request, store)

  initialize()
}
