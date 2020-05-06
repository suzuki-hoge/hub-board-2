package command.domain.issue

import command.domain.milestone.{CurrentMilestoneName, MilestoneName}

case class CreateRequest(creation: Creation)

object CreateRequest {
  def of(
             reqT: Title, reqB: Option[Body], reqLs: LabelNames, reqAs: AssigneeNames, reqP: Option[PipelineId],
             reqM: Option[MilestoneName], reqE: Option[Estimate], cm: CurrentMilestoneName): CreateRequest = CreateRequest(
    Creation(reqT, reqB, reqLs, reqAs, reqP, cm ifMissing reqM, Estimate.zero ifMissing reqE)
  )
}

case class CopyRequest(creation: Creation)

case class CutRequest(creation: Creation, comment: CutComment, sub: EstimateSubtraction, closing: Option[OriginIssueClosing])

case class CrunchRequests(reqs: Seq[CrunchRequest], sub: EstimateSubtraction, closing: Option[OriginIssueClosing])

case class CrunchRequest(creation: Creation, comment: CrunchComment)

case class Creation(t: Title, b: Option[Body], ls: LabelNames, as: AssigneeNames, p: Option[PipelineId], m: MilestoneName, e: Estimate)

case class EstimateSubtraction(n: IssueNumber, e: Estimate)

case class OriginIssueClosing(n: IssueNumber)
