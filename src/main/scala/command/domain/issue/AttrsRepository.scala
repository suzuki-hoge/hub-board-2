package command.domain.issue

import command.domain.milestone.{CurrentMilestoneName, MilestoneName}

trait AttrsRepository {
  def labels(): Seq[Label]

  def assignees(): Seq[Assignee]

  def pipelines(): Seq[Pipeline]

  def milestones(): Seq[MilestoneName] // todo refactor

  def currentMilestone(): CurrentMilestoneName // todo refactor
}
