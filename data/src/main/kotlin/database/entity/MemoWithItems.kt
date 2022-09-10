package repository.room.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import database.entity.ItemEntity
import database.entity.ItemEntity.Companion.toItem
import database.entity.MemoEntity

data class MemoWithItems(
    @Embedded val memo: MemoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "memoId"
    )
    val items: List<ItemEntity>
) {
    companion object {
        fun MemoWithItems.toMemo() = Memo.reconstruct(
            id = MemoId(memo.id),
            title = MemoTitle(memo.title),
            items = items.map { it.toItem() },
            accessedAt = memo.accessedAt,
        )
    }
}
