package com.wsr.memo

import java.util.Collections
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

class Memo private constructor(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<Item>,
    val accessedAt: Instant,
) {
    fun updateTitle(title: MemoTitle): Memo = update(
        title = title,
        items = items,
    )

    fun updateItemContent(itemId: ItemId, content: ItemContent): Memo =
        mapSpecifyItem(itemId) { it.updateContent(content) }

    fun updateItemChecked(itemId: ItemId): Memo = mapSpecifyItem(itemId) { it.switchChecked() }

    fun addItem(): Memo = mapItems { it + Item.create() }

    fun deleteCheckedItems(): Memo = mapItems { items -> items.filterNot { it.checked } }

    fun swapItem(from: ItemId, to: ItemId): Memo = mapItems {
        val fromIndex = items.indexOfFirst { it.id == from }
        val toIndex = items.indexOfFirst { it.id == to }
        if (fromIndex != -1 && toIndex != -1) {
            items.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        } else items
    }

    fun divideMemo(newTitle: MemoTitle): Pair<Memo, Memo> =
        items
            .partition { !it.checked }
            .let { (original, new) ->
                mapItems { original } to create(newTitle, new.map { it.switchChecked() })
            }

    private fun update(title: MemoTitle, items: List<Item>): Memo = reconstruct(
        id = id,
        title = title,
        items = items,
        accessedAt = Clock.System.now(),
    )

    private fun mapItems(block: (List<Item>) -> List<Item>): Memo =
        update(title, block(items))

    private fun mapSpecifyItem(itemId: ItemId, block: (Item) -> Item): Memo =
        mapItems { items ->
            items.map { item ->
                if (item.id == itemId) block(item) else item
            }
        }

    companion object {
        fun create(
            title: MemoTitle,
            items: List<Item> = listOf(Item.create()),
        ): Memo = Memo(
            id = MemoId(UUID.randomUUID().toString()),
            title = title,
            items = items,
            accessedAt = Clock.System.now(),
        )

        fun reconstruct(
            id: MemoId,
            title: MemoTitle,
            items: List<Item>,
            accessedAt: Instant,
        ): Memo = Memo(
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
