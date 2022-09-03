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
        fun MemoApiModel.toDomain(): Memo = Memo(
            id = MemoId(id),
            title = MemoTitle(title),
            items = items.map { it.toDomain() },
        )
    }
}

data class ItemApiModel(
    val id: String,
    val checked: Boolean,
    val content: String,
) {
    companion object {
        fun ItemApiModel.toDomain(): Item = Item(
            id = ItemId(id),
            checked = checked,
            content = ItemContent(content),
        )
    }
}
