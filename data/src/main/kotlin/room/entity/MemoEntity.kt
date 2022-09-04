package repository.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import api.MemoApiModel
import kotlinx.datetime.Instant
import repository.room.entity.ItemEntity.Companion.toApiModel

@Entity(tableName = "memos")
data class MemoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val accessedAt: Instant,
) {
    companion object {
        fun MemoEntity.toApiModel(items: List<ItemEntity>) = MemoApiModel(
            id = id,
            title = title,
            items = items.sortedBy { it.index }.map { it.toApiModel() },
            accessedAt = accessedAt,
        )

        fun from(memo: MemoApiModel) = MemoEntity(
            id = memo.id,
            title = memo.title,
            accessedAt = memo.accessedAt,
        )
    }
}
