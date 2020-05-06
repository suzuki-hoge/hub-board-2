package command.domain.issue

trait IssueRepository {
  def findOne(n: IssueNumber): Issue

  def create(req: CreateRequest): Issue

  def cut(req: CutRequest): Issue

  def crunch(reqs: CrunchRequests): Seq[Issue]
}
