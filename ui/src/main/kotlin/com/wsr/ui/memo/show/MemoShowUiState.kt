package com.wsr.ui.memo.show

import com.wsr.create.CreateItemUseCaseModel
import com.wsr.get.GetMemoByIdItemUseCaseModel
import com.wsr.get.GetMemoByIdUseCaseModel
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import com.wsr.ui.memo.show.MemoShowItemUiState.Companion.toUpdateMemoUseCaseModel
import com.wsr.update.UpdateMemoItemUseCaseModel
import com.wsr.update.UpdateMemoUseCaseModel

data class MemoShowUiState(
    val title: String = "",
    val items: List<MemoShowItemUiState> = listOf(),
) {
    companion object {
        fun from(fetchMemoByIdUseCaseModel: GetMemoByIdUseCaseModel) = MemoShowUiState(
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
        fun from(fetchMemoByIdItemUseCaseModel: GetMemoByIdItemUseCaseModel) =
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
