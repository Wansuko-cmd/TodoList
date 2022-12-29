package com.wsr.command

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
import kotlinx.coroutines.launch

class UpdateMemoTitleUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(memo: MemoUseCaseModel, title: MemoTitle) =
        withContext(dispatcher) {
            val newMemo = memo.toMemo().updateTitle(title)
            launch { memoRepository.upsert(newMemo) }
            MemoUseCaseModel.from(newMemo)
        }
}
