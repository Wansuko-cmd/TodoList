package com.wsr.command.element

import com.wsr.MemoUseCaseModel
import com.wsr.memo.Memo
import com.wsr.memo.MemoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend inline fun updateMemoAndReturn(
    memo: MemoUseCaseModel,
    memoRepository: MemoRepository,
    dispatcher: CoroutineDispatcher,
    crossinline block: (Memo) -> Memo,
) =
    withContext(dispatcher) {
        val newMemo = memo.toMemo().let(block)
        launch { memoRepository.upsert(newMemo) }
        MemoUseCaseModel.from(newMemo)
    }
