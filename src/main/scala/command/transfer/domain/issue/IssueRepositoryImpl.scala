package command.transfer.domain.issue

import command.domain.issue._
import command.domain.milestone.MilestoneName

object IssueRepositoryImpl extends IssueRepository {
  override def findOne(n: IssueNumber): Issue = {
    println(n)

    Issue(IssueNumber(1), Title("title"), Body("no body"), LabelNames(Seq()), AssigneeNames(Seq()), PipelineId("doing"), MilestoneName("sprint 1"), Estimate.zero)
  }

  override def create(req: CreateRequest): Issue = {
    println(req)

    // validate

    Issue(IssueNumber(1), req.creation.t, req.creation.b.getOrElse(Body("no body")), req.creation.ls, req.creation.as, req.creation.p.getOrElse(PipelineId("doing")), req.creation.m, req.creation.e)
  }

  override def cut(req: CutRequest): Issue = {
    println(req)

    Issue(IssueNumber(1), req.creation.t, req.creation.b.getOrElse(Body("no body")), req.creation.ls, req.creation.as, req.creation.p.getOrElse(PipelineId("doing")), req.creation.m, req.creation.e)
  }

  override def crunch(reqs: CrunchRequests): Seq[Issue] = {
    println(reqs)

    Seq()
  }
}
