package command.domain.issue

trait IssueRepository {
  def findOne(n: IssueNumber): Issue

  def create(n: IssueNumber): Issue

  def cut(n: IssueNumber): Issue

  def crunch(n: IssueNumber): Issue
}
