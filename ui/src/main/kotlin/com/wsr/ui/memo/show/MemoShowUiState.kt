package com.wsr.ui.memo.show

import com.wsr.get.GetMemoByIdItemUseCaseModel
import com.wsr.get.GetMemoByIdUseCaseModel
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoTitle

data class MemoShowUiState(
    val title: MemoTitle = MemoTitle(""),
    val items: List<MemoShowItemUiState> = listOf(),
    val shouldFocusItemId: ItemId? = null,
    val isLoading: Boolean = false,
    val isShowingEditMemoTitleDialog: Boolean = false,
) {

    fun mapItems(
        block: (List<MemoShowItemUiState>) -> List<MemoShowItemUiState>,
    ): MemoShowUiState = this.copy(items = block(items))

    companion object {
        fun from(fetchMemoByIdUseCaseModel: GetMemoByIdUseCaseModel) = MemoShowUiState(
            title = fetchMemoByIdUseCaseModel.title,
            items = fetchMemoByIdUseCaseModel.items.map { MemoShowItemUiState.from(it) },
        )
    }
}

data class MemoShowItemUiState(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun from(fetchMemoByIdItemUseCaseModel: GetMemoByIdItemUseCaseModel) =
            MemoShowItemUiState(
                id = fetchMemoByIdItemUseCaseModel.id,
                checked = fetchMemoByIdItemUseCaseModel.checked,
                content = fetchMemoByIdItemUseCaseModel.content,
            )
    }
}
