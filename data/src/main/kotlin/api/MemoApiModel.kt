package api

import api.ItemApiModel.Companion.toDomain
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle

data class MemoApiModel(
    val id: String,
    val title: String,
    val items: List<ItemApiModel>
) {
    companion object {
        fun MemoApiModel.toDomain(): Memo = Memo.reconstruct(
            id = MemoId(id),
            title = MemoTitle(title),
            items = items.map { it.toDomain() },
        )

        fun from(memo: Memo) = MemoApiModel(
            id = memo.id.value,
            title = memo.title.value,
            items = memo.items.map { ItemApiModel.from(it) }
        )
    }
}

data class ItemApiModel(
    val id: String,
    val checked: Boolean,
    val content: String,
) {
    companion object {
        fun ItemApiModel.toDomain(): Item = Item.reconstruct(
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
