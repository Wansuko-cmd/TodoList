package database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle

data class MemoWithItems(
    @Embedded val memo: MemoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "memoId",
    )
    val items: List<ItemEntity>,
) {
    fun toMemo(): Memo = Memo.reconstruct(
        id = MemoId(memo.id),
        title = MemoTitle(memo.title),
        items = items.map { it.toItem() },
        accessedAt = memo.accessedAt,
    )
}
