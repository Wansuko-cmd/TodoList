package com.wsr.ui.memo.show

import com.wsr.ItemUseCaseModel
import com.wsr.MemoUseCaseModel
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle

data class MemoShowUiState(
    val title: MemoTitle = MemoTitle(""),
    val items: List<MemoShowItemUiState> = listOf(),
    val shouldFocusItemId: ItemId? = null,
    val isLoading: Boolean = false,
    val isShowingUpdateMemoTitleDialog: Boolean = false,
    val isShowingEditDivideMemoTitleDialog: Boolean = false,
) {

    fun toUseCaseModel(id: MemoId) = MemoUseCaseModel(
        id = id,
        title = title,
        items = items.map { it.toUseCaseModel() },
    )

    companion object {
        fun from(memo: MemoUseCaseModel) = MemoShowUiState(
            title = memo.title,
            items = memo.items.map { MemoShowItemUiState.from(it) },
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
        fun from(item: ItemUseCaseModel) = MemoShowItemUiState(
            id = item.id,
            checked = item.checked,
            content = item.content,
        )
    }
}
