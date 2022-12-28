package com.wsr.get

import com.wsr.exception.DomainException
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.ApiResult
import com.wsr.result.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
) {

    operator fun invoke(): Flow<ApiResult<List<GetAllMemoUseCaseModel>, DomainException>> =
        memoRepository.getAllFlow()
            .map { data ->
                data.map { memos -> memos.sortedByDescending { it.accessedAt } }
                    .map { memos -> memos.map(GetAllMemoUseCaseModel::from) }
            }
}

data class GetAllMemoUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
) {
    companion object {
        fun from(memo: Memo) =
            GetAllMemoUseCaseModel(
                id = memo.id,
                title = memo.title,
            )
    }
}
