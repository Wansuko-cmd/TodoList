package repository.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MemoWithItems(
    @Embedded val memo: MemoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "memoId"
    )
    val items: List<ItemEntity>
)
