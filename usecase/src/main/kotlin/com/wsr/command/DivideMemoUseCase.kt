package com.wsr.command

import com.wsr.MemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.map
import com.wsr.result.onEach
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
    ) = withContext(dispatcher) {
        memoRepository.getById(originalMemoId)
            .map { it.divideMemo(newTitle) }
            .onEach { (original, new) ->
                memoRepository.upsert(original)
                memoRepository.upsert(new)
            }
            .map { (original, new) ->
                MemoUseCaseModel.from(original) to MemoUseCaseModel.from(new)
            }
    }
}
