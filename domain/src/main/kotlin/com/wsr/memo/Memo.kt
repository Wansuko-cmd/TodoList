package com.wsr.memo

data class Memo(
    val id: MemoId,
    val items: List<Item>,
)

@JvmInline
value class MemoId(val value: String)
