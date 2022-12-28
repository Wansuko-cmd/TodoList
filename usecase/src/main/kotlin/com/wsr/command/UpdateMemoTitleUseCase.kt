package com.wsr.command

import com.wsr.di.DefaultDispatcher
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.flatMap
import com.wsr.result.map
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateMemoTitleUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(memoId: MemoId, title: MemoTitle) =
        withContext(dispatcher) {
            memoRepository
                .getById(memoId)
                .map { memo -> memo.updateTitle(title) }
                .flatMap { memoRepository.upsert(it) }
        }
}