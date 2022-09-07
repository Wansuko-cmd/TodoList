package com.wsr.create

import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import javax.inject.Inject

class CreateItemUseCase @Inject constructor() {
    operator fun invoke() = Item.create().let(CreateItemUseCaseModel::from)
}

data class CreateItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun from(item: Item) = CreateItemUseCaseModel(
            id = item.id,
            checked = item.checked,
            content = item.content,
        )
    }
}
