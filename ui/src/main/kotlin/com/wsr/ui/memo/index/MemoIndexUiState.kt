package com.wsr.ui.memo.index

import com.wsr.MemoUseCaseModel

data class MemoIndexUiState(
    val memos: List<MemoIndexMemoUiState> = listOf(),
    val isLoading: Boolean = false,
    val isShowingCreateMemoDialog: Boolean = false,
) {
    companion object {
        fun from(memos: List<MemoUseCaseModel>) =
            MemoIndexUiState(memos = memos.map { MemoIndexMemoUiState.from(it) })
    }
}

data class MemoIndexMemoUiState(
    val id: String,
    val title: String,
) {
    companion object {
        fun from(memo: MemoUseCaseModel) =
            MemoIndexMemoUiState(
                id = memo.id.value,
                title = memo.title.value,
            )
    }
}
