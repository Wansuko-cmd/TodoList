package com.wsr.ui.memo.show

import com.wsr.FetchMemoByIdItemUseCaseModel
import com.wsr.FetchMemoByIdUseCaseModel

data class MemoShowUiState(
    val title: String = "",
    val items: List<MemoShowItemUiState> = listOf(),
) {
    companion object {
        fun from(fetchMemoByIdUseCaseModel: FetchMemoByIdUseCaseModel) = MemoShowUiState(
            title = fetchMemoByIdUseCaseModel.title.value,
            items = fetchMemoByIdUseCaseModel.items.map { MemoShowItemUiState.from(it) },
        )
    }
}

data class MemoShowItemUiState(
    val id: String,
    val checked: Boolean,
    val content: String,
) {
    companion object {
        fun from(fetchMemoByIdItemUseCaseModel: FetchMemoByIdItemUseCaseModel) =
            MemoShowItemUiState(
                id = fetchMemoByIdItemUseCaseModel.id.value,
                checked = fetchMemoByIdItemUseCaseModel.checked,
                content = fetchMemoByIdItemUseCaseModel.content.value,
            )
    }
}
