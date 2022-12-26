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
    val title: MemoTitle = MemoTitle(""),
    val items: List<MemoShowItemUiState> = listOf(),
    val shouldFocusItemId: ItemId? = null,
    val isLoading: Boolean = false,
) {

    fun mapItems(
        block: (List<MemoShowItemUiState>) -> List<MemoShowItemUiState>,
    ): MemoShowUiState = this.copy(items = block(items))

    fun toUpdateMemoUseCaseModel(memoId: String) = UpdateMemoUseCaseModel(
        id = MemoId(memoId),
        title = title,
        items = items.map { it.toUpdateMemoUseCaseModel() }
    )

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

        fun from(createItemUseCaseModel: CreateItemUseCaseModel) =
            MemoShowItemUiState(
                id = createItemUseCaseModel.id,
                checked = createItemUseCaseModel.checked,
                content = createItemUseCaseModel.content,
            )

        fun MemoShowItemUiState.toUpdateMemoUseCaseModel() = UpdateMemoItemUseCaseModel(
            id = id,
            checked = checked,
            content = content,
        )
    }
}
