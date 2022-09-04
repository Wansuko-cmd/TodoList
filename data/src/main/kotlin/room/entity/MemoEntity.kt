package repository.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import api.MemoApiModel
import repository.room.entity.ItemEntity.Companion.toApiModel

@Entity(tableName = "memos")
data class MemoEntity(
    @PrimaryKey val id: String,
    val title: String,
) {
    companion object {
        fun MemoEntity.toApiModel(items: List<ItemEntity>) = MemoApiModel(
            id = id,
            title = title,
            items = items.sortedBy { it.index }.map { it.toApiModel() }
        )

        fun from(memo: MemoApiModel) = MemoEntity(
            id = memo.id,
            title = memo.title,
        )
    }
}
