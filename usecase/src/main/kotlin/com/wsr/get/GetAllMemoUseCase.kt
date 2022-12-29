package com.wsr.get

import com.wsr.MemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.MemoRepository
import com.wsr.result.ApiResult
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(): ApiResult<List<MemoUseCaseModel>, DomainException> =
        withContext(dispatcher) {
            memoRepository.getAll()
                .map { memos -> memos.sortedByDescending { it.accessedAt } }
                .map { memos -> memos.map(MemoUseCaseModel::from) }
        }
}
