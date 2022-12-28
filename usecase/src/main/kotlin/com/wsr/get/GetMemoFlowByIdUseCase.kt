package com.wsr.get

import com.wsr.di.DefaultDispatcher
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMemoFlowByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(memoId: MemoId) = withContext(dispatcher) {
        memoRepository.getById(memoId).map(GetMemoFlowByIdUseCaseModel::from)
    }.let { flowOf(it) }
}

data class GetMemoFlowByIdUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<GetMemoByIdItemUseCaseModel>,
) {
    companion object {
        fun from(memo: Memo) = GetMemoFlowByIdUseCaseModel(
            id = memo.id,
            title = memo.title,
            items = memo.items.map(GetMemoByIdItemUseCaseModel::from),
        )
    }
}

data class GetMemoFlowByIdItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun from(item: Item) = GetMemoFlowByIdItemUseCaseModel(
            id = item.id,
            checked = item.checked,
            content = item.content,
        )
    }
}
