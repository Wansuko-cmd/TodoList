package com.wsr.ui.memo.show

import com.wsr.CreateItemUseCaseModel
import com.wsr.FetchMemoByIdItemUseCaseModel
import com.wsr.FetchMemoByIdUseCaseModel
import com.wsr.UpdateMemoItemUseCaseModel
import com.wsr.UpdateMemoUseCaseModel
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import com.wsr.ui.memo.show.MemoShowItemUiState.Companion.toUpdateMemoUseCaseModel

data class MemoShowUiState(
    val title: String = "",
    val items: List<MemoShowItemUiState> = listOf(),
) {
    companion object {
        fun from(fetchMemoByIdUseCaseModel: FetchMemoByIdUseCaseModel) = MemoShowUiState(
            title = fetchMemoByIdUseCaseModel.title.value,
            items = fetchMemoByIdUseCaseModel.items.map { MemoShowItemUiState.from(it) },
        )

        fun MemoShowUiState.toUpdateMemoUseCaseModel(memoId: String) = UpdateMemoUseCaseModel(
            id = MemoId(memoId),
            title = MemoTitle(title),
            items = items.map { it.toUpdateMemoUseCaseModel() }
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

        fun from(createItemUseCaseModel: CreateItemUseCaseModel) =
            MemoShowItemUiState(
                id = createItemUseCaseModel.id.value,
                checked = createItemUseCaseModel.checked,
                content = createItemUseCaseModel.content.value,
            )

        fun MemoShowItemUiState.toUpdateMemoUseCaseModel() = UpdateMemoItemUseCaseModel(
            id = ItemId(id),
            checked = checked,
            content = ItemContent(content),
        )
    }
}
