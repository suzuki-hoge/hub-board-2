package boot.transfer

import boot.domain.{Store, StoreRepository}
import command.domain.issue.LabelNames
import command.domain.milestone.CurrentMilestoneName

object StoreRepositoryImpl extends StoreRepository {
  override def initialize: Store = {
    Store(
      LabelNames(Seq()),
      CurrentMilestoneName("spring 1")
    )
  }
}
