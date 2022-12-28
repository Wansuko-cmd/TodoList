package com.wsr.command

import com.wsr.di.DefaultDispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.ApiResult
import com.wsr.result.flatMap
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DivideMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(
        originalMemoId: MemoId,
        newTitle: MemoTitle,
    ): ApiResult<Unit, DomainException> = withContext(dispatcher) {
        memoRepository.getById(originalMemoId)
            .divideMemo(newTitle)
            .flatMap { (original, new) ->
                memoRepository.upsert(original)
                memoRepository.upsert(new)
            }
    }

    private fun ApiResult<Memo, DomainException>.divideMemo(
        newTitle: MemoTitle,
    ): ApiResult<Pair<Memo, Memo>, DomainException> = this.map { memo ->
        memo
            .items
            .partition { !it.checked }
            .let { (original, new) ->
                Memo.reconstruct(memo.id, memo.title, original) to Memo.create(newTitle, new)
            }
    }
}
