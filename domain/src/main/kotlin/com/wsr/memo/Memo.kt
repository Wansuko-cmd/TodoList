package com.wsr.memo

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

class Memo private constructor(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<Item>,
    val accessedAt: Instant,
) {
    fun updateTitle(title: MemoTitle) = update(
        title = title,
        items = items,
    )

    fun updateItemContent(itemId: ItemId, content: ItemContent) =
        updateSpecifyItem(itemId) { it.updateContent(content) }

    fun updateItemChecked(itemId: ItemId) = updateSpecifyItem(itemId) { it.changeChecked() }

    fun deleteCheckedItems() = updateItems(items.filterNot { it.checked })

    fun swapItem(from: ItemId, to: ItemId): Memo {
        val fromIndex = items.indexOfFirst { it.id == from }
        val toIndex = items.indexOfFirst { it.id == to }
        val items = if (fromIndex != -1 && toIndex != -1) {
            items.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        } else items
        return updateItems(items)
    }

    fun addItem() = updateItems(items + Item.create())

    private fun update(title: MemoTitle, items: List<Item>) = reconstruct(
        id = id,
        title = title,
        items = items,
        accessedAt = Clock.System.now(),
    )

    private fun updateItems(items: List<Item>) = update(title, items)

    private fun updateSpecifyItem(itemId: ItemId, block: (Item) -> Item) = update(
        title = title,
        items = items.map { item -> if (item.id == itemId) block(item) else item },
    )

    companion object {
        fun create(
            title: MemoTitle,
            items: List<Item> = listOf(Item.create()),
        ) = Memo(
            id = MemoId(UUID.randomUUID().toString()),
            title = title,
            items = items,
            accessedAt = Clock.System.now(),
        )

        fun reconstruct(
            id: MemoId,
            title: MemoTitle,
            items: List<Item>,
            accessedAt: Instant = Clock.System.now(),
        ) = Memo(
            id = id,
            title = title,
            items = items,
            accessedAt = accessedAt,
        )
    }
}

@JvmInline
value class MemoId(val value: String)

@JvmInline
value class MemoTitle(val value: String)
