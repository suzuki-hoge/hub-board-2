package command.domain.issue

case class IssueNumber(v: Int)

case class BaseTitle(v: String)

case class Title(v: String) {
  def joined(bt: Option[BaseTitle]): Title = bt match {
    case Some(BaseTitle(prefix)) => Title(s"$prefix - $v")
    case None => this
  }
}

case class Body(v: String)

case class LabelNames(ls: Seq[LabelName]) {
  def ifMissing(other: LabelNames): LabelNames = if (other.nonEmpty) other else this

  private def nonEmpty: Boolean = ls.nonEmpty
}

case class LabelName(v: String)

case class AssigneeNames(as: Seq[AssigneeName]) {
  def ifMissing(other: AssigneeNames): AssigneeNames = if (other.nonEmpty) other else this

  private def nonEmpty: Boolean = as.nonEmpty
}

case class AssigneeName(v: String)

case class PipelineId(v: String) {
  def ifMissing(other: Option[PipelineId]): Option[PipelineId] = other.orElse(Some(this))
}

case class Estimate(v: Float) {
  def <(other: Estimate): Boolean = v < other.v

  def +(other: Estimate): Estimate = Estimate(other.v + v)

  def ifMissing(other: Option[Estimate]): Estimate = other.getOrElse(this)
}

object Estimate {
  def zero: Estimate = Estimate(0)
}
