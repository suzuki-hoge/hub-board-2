package boot

import command.domain.issue._
import command.domain.milestone.{CurrentMilestoneName, MilestoneName}
import command.transfer.domain.issue.AttrsRepositoryImpl

case class Store(ls: Seq[Label], as: Seq[Assignee], ms: Seq[MilestoneName], ps: Seq[Pipeline], cm: CurrentMilestoneName)

object Store {
  private val repo = AttrsRepositoryImpl

  private var x: Option[Store] = None

  def apply(): Store = x.getOrElse(initialize)

  private def initialize: Store = {
    val config = Config()

    val x = Store(
      repo.labels().filter(l => config.ignoreLs.contains(l.name)),
      repo.assignees(),
      repo.milestones(),
      repo.pipelines(),
      repo.currentMilestone()
    )

    this.x = Some(x)

    x
  }
}
