package com.wsr.create

import com.wsr.create.CreateItemUseCaseModel.Companion.toCreateItemUseCaseModel
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import javax.inject.Inject

class CreateItemUseCase @Inject constructor() {
    operator fun invoke() = Item.create().toCreateItemUseCaseModel()
}

data class CreateItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun Item.toCreateItemUseCaseModel() = CreateItemUseCaseModel(
            id = id,
            checked = checked,
            content = content,
        )
    }
}
