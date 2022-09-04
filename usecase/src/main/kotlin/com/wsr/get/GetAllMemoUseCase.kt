package com.wsr.get

import com.wsr.get.GetAllMemoUseCaseModel.Companion.toGetAllMemoUseCaseModel
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke() = withContext(dispatcher) {
        memoRepository.getAll().map { memos -> memos.map { it.toGetAllMemoUseCaseModel() } }
    }
}

data class GetAllMemoUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
) {
    companion object {
        fun Memo.toGetAllMemoUseCaseModel() =
            GetAllMemoUseCaseModel(
                id = id,
                title = title,
            )
    }
}
