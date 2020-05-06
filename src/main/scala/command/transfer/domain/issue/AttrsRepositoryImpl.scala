package command.transfer.domain.issue

import command.domain.issue._
import command.domain.milestone.{CurrentMilestoneName, MilestoneName}

object AttrsRepositoryImpl extends AttrsRepository {
  override def labels(): Seq[Label] = {
    Seq()
  }

  override def assignees(): Seq[Assignee] = {
    Seq()
  }

  override def pipelines(): Seq[Pipeline] = {
    Seq()
  }

  override def milestones(): Seq[MilestoneName] = {
    Seq()
  }

  override def currentMilestone(): CurrentMilestoneName = {
    CurrentMilestoneName("sprint 1")
  }
}
