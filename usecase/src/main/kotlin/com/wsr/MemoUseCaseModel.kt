package com.wsr

import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import kotlinx.datetime.Clock

data class MemoUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<ItemUseCaseModel>,
) {
    fun toMemo(): Memo = Memo.reconstruct(
        id = id,
        title = title,
        items = items.map { it.toItem() },
        accessedAt = Clock.System.now(),
    )

    companion object {
        fun from(memo: Memo): MemoUseCaseModel = MemoUseCaseModel(
            id = memo.id,
            title = memo.title,
            items = memo.items.map(ItemUseCaseModel::from),
        )
    }
}

data class ItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    fun toItem(): Item = Item.reconstruct(
        id = id,
        checked = checked,
        content = content,
    )

    companion object {
        fun from(item: Item): ItemUseCaseModel = ItemUseCaseModel(
            id = item.id,
            checked = item.checked,
            content = item.content,
        )
    }
}
