package com.wsr.ui.memo.show

import com.wsr.ItemUseCaseModel
import com.wsr.MemoUseCaseModel
import com.wsr.get.GetMemoByIdItemUseCaseModel
import com.wsr.get.GetMemoByIdUseCaseModel
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle

data class MemoShowUiState(
    val title: MemoTitle = MemoTitle(""),
    val items: List<MemoShowItemUiState> = listOf(),
    val shouldFocusItemId: ItemId? = null,
    val isLoading: Boolean = false,
    val isShowingEditMemoTitleDialog: Boolean = false,
) {

    fun toUseCaseModel(id: MemoId) = MemoUseCaseModel(
        id = id,
        title = title,
        items = items.map { it.toUseCaseModel() },
    )

    fun mapItems(
        block: (List<MemoShowItemUiState>) -> List<MemoShowItemUiState>,
    ): MemoShowUiState = this.copy(items = block(items))

    companion object {
        fun fromUseCaseModel(memo: MemoUseCaseModel) = MemoShowUiState(
            title = memo.title,
            items = memo.items.map { MemoShowItemUiState.fromUseCaseModel(it) },
        )
    }
}

data class MemoShowItemUiState(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    fun toUseCaseModel() = ItemUseCaseModel(
        id = id,
        checked = checked,
        content = content,
    )
    companion object {
        fun fromUseCaseModel(item: ItemUseCaseModel) = MemoShowItemUiState(
            id = item.id,
            checked = item.checked,
            content = item.content,
        )
    }
}
