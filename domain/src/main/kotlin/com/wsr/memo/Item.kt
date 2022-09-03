package com.wsr.memo

import java.util.*

class Item private constructor(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun create(content: ItemContent) = Item(
            id = ItemId(UUID.randomUUID().toString()),
            checked = false,
            content = content,
        )

        fun reconstruct(
            id: ItemId,
            checked: Boolean,
            content: ItemContent,
        ) = Item(
            id = id,
            checked = checked,
            content = content,
        )
    }
}

@JvmInline
value class ItemId(val value: String)

@JvmInline
value class ItemContent(val value: String)
