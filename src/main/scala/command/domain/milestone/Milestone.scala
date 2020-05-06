package command.domain.milestone

case class Milestone(n: MilestoneName, s: Start, e: End)

case class MilestoneName(v: String) {
  def ifMissing(other: Option[MilestoneName]): MilestoneName = other.getOrElse(MilestoneName(v))
}

case class Start(v: String)

case class End(v: String)

case class CurrentMilestoneName(v: String) {
  def ifMissing(other: Option[MilestoneName]): MilestoneName = other.getOrElse(MilestoneName(v))
}