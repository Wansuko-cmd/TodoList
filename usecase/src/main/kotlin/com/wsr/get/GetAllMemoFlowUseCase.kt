package com.wsr.get

import com.wsr.di.DefaultDispatcher
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

class GetAllMemoFlowUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke() = withContext(dispatcher) {
        memoRepository.getAll()
            .map { memos -> memos.sortedByDescending { it.accessedAt } }
            .map { memos -> memos.map(GetAllMemoFlowUseCaseModel::from) }
    }.let { flowOf(it) }
}

data class GetAllMemoFlowUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
) {
    companion object {
        fun from(memo: Memo) =
            GetAllMemoFlowUseCaseModel(
                id = memo.id,
                title = memo.title,
            )
    }
}
