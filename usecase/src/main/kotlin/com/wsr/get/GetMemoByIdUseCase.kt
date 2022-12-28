package com.wsr.get

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMemoByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
) {

    operator fun invoke(memoId: MemoId): Flow<ApiResult<GetMemoByIdUseCaseModel, DomainException>> = memoRepository.getFlowById(memoId)
        .map { data -> data.map { GetMemoByIdUseCaseModel.from(it) } }
}

data class GetMemoByIdUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<GetMemoByIdItemUseCaseModel>,
) {
    companion object {
        fun from(memo: Memo) = GetMemoByIdUseCaseModel(
            id = memo.id,
            title = memo.title,
            items = memo.items.map(GetMemoByIdItemUseCaseModel::from),
        )
    }
}

data class GetMemoByIdItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun from(item: Item) = GetMemoByIdItemUseCaseModel(
            id = item.id,
            checked = item.checked,
            content = item.content,
        )
    }
}
