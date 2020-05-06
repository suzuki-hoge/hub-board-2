package complete.domain

sealed trait Candidate

case class Label(value: String, color: String) extends Candidate

case class Assignee(value: String, iconUrl: String) extends Candidate

case class PipelineNames(value: String) extends Candidate

case class MilestoneName(value: String) extends Candidate

object NoCandidate extends Candidate

case class OptionKey(value: String, desc: String) extends Candidate

object OptionKey {
  val t = OptionKey("t", "title")
  val b = OptionKey("b", "body")
  val l = OptionKey("l", "label")
  val a = OptionKey("a", "assignee")
  val p = OptionKey("p", "pipeline")
  val m = OptionKey("m", "milestone")
  val e = OptionKey("e", "estimate")

  val all: Seq[OptionKey] = Seq(t, b, l, a, p, m, e)
}
