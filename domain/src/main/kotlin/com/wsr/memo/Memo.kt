package com.wsr.memo

import java.util.*

class Memo private constructor(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<Item>,
) {
    companion object {
        fun create(title: MemoTitle) = Memo(
            id = MemoId(UUID.randomUUID().toString()),
            title = title,
            items = listOf(),
        )

        fun reconstruct(
            id: MemoId,
            title: MemoTitle,
            items: List<Item>,
        ) = Memo(
            id = id,
            title = title,
            items = items,
        )
    }
}

@JvmInline
value class MemoId(val value: String)

@JvmInline
value class MemoTitle(val value: String)
