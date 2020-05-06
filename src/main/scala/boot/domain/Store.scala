package boot.domain

import command.domain.issue.LabelNames
import command.domain.milestone.CurrentMilestoneName

case class Store(ls: LabelNames, cm: CurrentMilestoneName)
