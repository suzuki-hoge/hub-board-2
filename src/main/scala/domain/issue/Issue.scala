package domain.issue

import domain.milestone._

case class Issue(n: IssueNumber, orgT: Title, orgB: Body, orgLs: LabelNames, orgAs: AssigneeNames, orgP: PipelineId, orgM: MilestoneName, orgE: Estimate) {
  def copy(reqT: Title, reqB: Option[Body], reqLs: LabelNames, reqAs: AssigneeNames, reqP: Option[PipelineId], reqM: Option[MilestoneName], reqE: Option[Estimate]): CopyRequest = {
    CopyRequest(
      Creation(reqT, reqB, orgLs ifMissing reqLs, orgAs ifMissing reqAs, orgP ifMissing reqP, orgM ifMissing reqM, orgE ifMissing reqE)
    )
  }

  def cut(reqT: Title, reqB: Option[Body], reqLs: LabelNames, reqAs: AssigneeNames, reqP: Option[PipelineId], reqM: Option[MilestoneName], reqE: Option[Estimate]): Either[Error, CutRequest] = {
    val e = Estimate.zero ifMissing reqE

    if (orgE < e)
      Left(OriginEstimateIsLessThanNewEstimate)
    else
      Right(
        CutRequest(
          Creation(reqT, reqB, orgLs ifMissing reqLs, orgAs ifMissing reqAs, orgP ifMissing reqP, orgM ifMissing reqM, e),
          CutComment(n),
          EstimateSubtraction(n, e),
          if (orgE == e) Some(OriginIssueClosing(n)) else None
        )
      )
  }

  def crunch(bt: Option[BaseTitle], tups: Seq[(Title, Option[Body], LabelNames, AssigneeNames, Option[PipelineId], Option[MilestoneName], Option[Estimate])]): CrunchRequests = {
    val reqs = tups.map {
      case (reqT, reqB, reqLs, reqAs, reqP, reqM, reqE) =>
        val e = Estimate.zero ifMissing reqE

        CrunchRequest(
          Creation(reqT joined bt, reqB, orgLs ifMissing reqLs, orgAs ifMissing reqAs, orgP ifMissing reqP, orgM ifMissing reqM, e),
          CrunchComment(n)
        )
    }

    val totalE = reqs.map(_.creation.e).reduceLeft((acc, e) => acc + e)

    CrunchRequests(
      reqs,
      EstimateSubtraction(n, totalE),
      if (totalE < orgE) None else Some(OriginIssueClosing(n))
    )
  }
}
