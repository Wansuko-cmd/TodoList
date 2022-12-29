package com.wsr.command.element

import com.wsr.MemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateMemoTitleUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(memo: MemoUseCaseModel, title: MemoTitle) =
        updateMemoAndReturn(memo, memoRepository, dispatcher) { it.updateTitle(title) }
}
