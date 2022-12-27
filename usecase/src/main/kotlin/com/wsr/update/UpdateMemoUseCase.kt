package com.wsr.update

import com.wsr.di.DefaultDispatcher
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import com.wsr.update.UpdateMemoItemUseCaseModel.Companion.toItem
import com.wsr.update.UpdateMemoUseCaseModel.Companion.toMemo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(memo: UpdateMemoUseCaseModel) =
        withContext(dispatcher) {
            memoRepository.upsert(memo.toMemo())
        }
}

data class UpdateMemoUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<UpdateMemoItemUseCaseModel>,
) {
    companion object {
        fun UpdateMemoUseCaseModel.toMemo() = Memo.reconstruct(
            id = id,
            title = title,
            items = items.map { it.toItem() },
        )
    }
}

data class UpdateMemoItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun UpdateMemoItemUseCaseModel.toItem() = Item.reconstruct(
            id = id,
            checked = checked,
            content = content,
        )
    }
}
