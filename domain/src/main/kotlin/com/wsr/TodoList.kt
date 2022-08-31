package com.wsr

data class TodoList(
    val id: TodoListId,
    val items: List<ItemId>,
)

@JvmInline
value class TodoListId(val value: String)
