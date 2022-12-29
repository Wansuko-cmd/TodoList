package com.wsr.command

import com.wsr.MemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateItemContentUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(
        memo: MemoUseCaseModel,
        itemId: ItemId,
        content: ItemContent,
    ) =
        withContext(dispatcher) {
            val newMemo = memo.toMemo().updateItemContent(itemId, content)
            launch { memoRepository.upsert(newMemo) }
            MemoUseCaseModel.from(newMemo)
        }
}
