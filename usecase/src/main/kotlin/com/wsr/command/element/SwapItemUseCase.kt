package com.wsr.command.element

import com.wsr.MemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.ItemId
import com.wsr.memo.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SwapItemUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @param:DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(
        memo: MemoUseCaseModel,
        from: ItemId,
        to: ItemId,
    ): MemoUseCaseModel =
        updateMemoAndReturn(memo, memoRepository, dispatcher) { it.swapItem(from, to) }
}
