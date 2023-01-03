package com.wsr.command.element

import com.wsr.MemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.ItemId
import com.wsr.memo.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateItemCheckedUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(
        memo: MemoUseCaseModel,
        itemId: ItemId,
    ): MemoUseCaseModel =
        updateMemoAndReturn(memo, memoRepository, dispatcher) { it.updateItemChecked(itemId) }
}
