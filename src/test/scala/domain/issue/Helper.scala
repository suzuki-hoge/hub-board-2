package domain.issue

import domain.milestone.{CurrentMilestoneName, MilestoneName}

object Helper {
  implicit def intToIssueNumber(n: Int): IssueNumber = IssueNumber(n)

  implicit def strToBaseTitleOpt(s: String): Option[BaseTitle] = Some(BaseTitle(s))

  implicit def strToTitle(s: String): Title = Title(s)

  implicit def strToBody(s: String): Body = Body(s)

  implicit def strToBodyOpt(s: String): Option[Body] = Some(Body(s))

  implicit def strToLabelNames(s: String): LabelNames = if (s == "") LabelNames(Seq()) else LabelNames(s.split(",").map(LabelName))

  implicit def strToAssigneeNames(s: String): AssigneeNames = if (s == "") AssigneeNames(Seq()) else AssigneeNames(s.split(",").map(AssigneeName))

  implicit def strToPipelineId(v: String): PipelineId = PipelineId(v)

  implicit def strToPipelineIdOpt(v: String): Option[PipelineId] = Some(PipelineId(v))

  implicit def strToMilestoneName(v: String): MilestoneName = MilestoneName(v)

  implicit def strToMilestoneNameOpt(v: String): Option[MilestoneName] = Some(MilestoneName(v))

  implicit def strToCurrentMilestoneName(v: String): CurrentMilestoneName = CurrentMilestoneName(v)

  implicit def intToEstimate(n: Int): Estimate = Estimate(n)

  implicit def intToEstimateOpt(n: Int): Option[Estimate] = Some(Estimate(n))

  implicit def intToCutComment(n: Int): CutComment = CutComment(IssueNumber(n))

  implicit def intToCrunchComment(n: Int): CrunchComment = CrunchComment(IssueNumber(n))

  implicit def intsToEstimateSubtraction(t: (Int, Int)): EstimateSubtraction = EstimateSubtraction(IssueNumber(t._1), Estimate(t._2))

  implicit def intToOriginIssueClosing(n: Int): Option[OriginIssueClosing] = Some(OriginIssueClosing(IssueNumber(n)))
}
