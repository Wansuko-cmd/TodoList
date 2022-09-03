package com.wsr.ui.memo.index

import com.wsr.FetchAllMemoUseCaseModel

data class MemoIndexUiState(
    val memos: List<MemoIndexMemoUiState> = listOf(),
) {
    companion object {
        fun from(memos: List<FetchAllMemoUseCaseModel>) =
            MemoIndexUiState(memos = memos.map { MemoIndexMemoUiState.from(it) })
    }
}

data class MemoIndexMemoUiState(
    val id: String,
    val title: String,
) {
    companion object {
        fun from(memo: FetchAllMemoUseCaseModel) =
            MemoIndexMemoUiState(
                id = memo.id.value,
                title = memo.title.value,
            )
    }
}
