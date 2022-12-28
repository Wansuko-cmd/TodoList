package com.wsr.get

import com.wsr.di.DefaultDispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.ApiResult
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMemoFlowByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    operator fun invoke(memoId: MemoId): Flow<ApiResult<GetMemoFlowByIdUseCaseModel, DomainException>> = memoRepository.getFlowById(memoId)
        .map { data -> data.map { GetMemoFlowByIdUseCaseModel.from(it) } }
}

data class GetMemoFlowByIdUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<GetMemoFlowByIdItemUseCaseModel>,
) {
    companion object {
        fun from(memo: Memo) = GetMemoFlowByIdUseCaseModel(
            id = memo.id,
            title = memo.title,
            items = memo.items.map(GetMemoFlowByIdItemUseCaseModel::from),
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
