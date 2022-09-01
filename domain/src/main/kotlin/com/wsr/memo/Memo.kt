package com.wsr.memo

data class Memo(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<Item>,
)

@JvmInline
value class MemoId(val value: String)

@JvmInline
value class MemoTitle(val value: String)
