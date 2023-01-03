package com.wsr.command.element

import com.wsr.MemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.flatMap
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMemoTitleUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(memo: MemoUseCaseModel, title: MemoTitle) =
        updateMemoAndReturn(memo, memoRepository, dispatcher) { it.updateTitle(title) }

    suspend operator fun invoke(memoId: MemoId, title: MemoTitle) =
        withContext(dispatcher) {
            memoRepository.getById(memoId)
                .map { it.updateTitle(title) }
                .flatMap { memoRepository.upsert(it) }
        }
}
