package com.wsr.get

import com.wsr.di.DefaultDispatcher
import com.wsr.get.GetMemoByIdItemUseCaseModel.Companion.toGetMemoByIdItemUseCaseModel
import com.wsr.get.GetMemoByIdUseCaseModel.Companion.toGetMemoByIdUseCaseModel
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMemoByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(memoId: MemoId) = withContext(dispatcher) {
        memoRepository.getById(memoId).map { it.toGetMemoByIdUseCaseModel() }
    }
}

data class GetMemoByIdUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<GetMemoByIdItemUseCaseModel>,
) {
    companion object {
        fun Memo.toGetMemoByIdUseCaseModel() = GetMemoByIdUseCaseModel(
            id = id,
            title = title,
            items = items.map { it.toGetMemoByIdItemUseCaseModel() },
        )
    }
}

data class GetMemoByIdItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun Item.toGetMemoByIdItemUseCaseModel() = GetMemoByIdItemUseCaseModel(
            id = id,
            checked = checked,
            content = content,
        )
    }
}
