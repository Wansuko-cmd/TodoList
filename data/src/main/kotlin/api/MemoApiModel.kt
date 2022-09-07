package api

import api.ItemApiModel.Companion.toItem
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import kotlinx.datetime.Instant

data class MemoApiModel(
    val id: String,
    val title: String,
    val items: List<ItemApiModel>,
    val accessedAt: Instant
) {
    companion object {
        fun MemoApiModel.toMemo(): Memo = Memo.reconstruct(
            id = MemoId(id),
            title = MemoTitle(title),
            items = items.map { it.toItem() },
            accessedAt = accessedAt,
        )

        fun from(memo: Memo) = MemoApiModel(
            id = memo.id.value,
            title = memo.title.value,
            items = memo.items.map { ItemApiModel.from(it) },
            accessedAt = memo.accessedAt,
        )
    }
}

data class ItemApiModel(
    val id: String,
    val checked: Boolean,
    val content: String,
) {
    companion object {
        fun ItemApiModel.toItem(): Item = Item.reconstruct(
            id = ItemId(id),
            checked = checked,
            content = ItemContent(content),
        )

        fun from(item: Item) = ItemApiModel(
            id = item.id.value,
            checked = item.checked,
            content = item.content.value,
        )
    }
}
