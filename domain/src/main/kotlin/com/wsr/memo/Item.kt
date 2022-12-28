package com.wsr.memo

import java.util.UUID

class Item private constructor(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    fun changeChecked() = reconstruct(
        id = id,
        checked = !checked,
        content = content,
    )

    companion object {
        fun create() = Item(
            id = ItemId(UUID.randomUUID().toString()),
            checked = false,
            content = ItemContent(""),
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
