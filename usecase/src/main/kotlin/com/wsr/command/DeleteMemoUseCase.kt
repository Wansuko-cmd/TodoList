package com.wsr.command

import com.wsr.di.DefaultDispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @param:DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(memoId: MemoId): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            memoRepository.delete(memoId)
        }
}
