package com.wsr.memo

data class Memo(
    val id: MemoId,
    val items: List<ItemId>,
)

@JvmInline
value class MemoId(val value: String)
