package com.wsr.update

import com.wsr.update.UpdateMemoItemUseCaseModel.Companion.toDomain
import com.wsr.update.UpdateMemoUseCaseModel.Companion.toDomain
import com.wsr.di.DefaultDispatcher
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.memo.MemoTitle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend operator fun invoke(memo: UpdateMemoUseCaseModel) =
        withContext(dispatcher) {
            memoRepository.upsert(memo.toDomain())
        }
}

data class UpdateMemoUseCaseModel(
    val id: MemoId,
    val title: MemoTitle,
    val items: List<UpdateMemoItemUseCaseModel>,
) {
    companion object {
        fun UpdateMemoUseCaseModel.toDomain() = Memo.reconstruct(
            id = id,
            title = title,
            items = items.map { it.toDomain() }
        )
    }
}

data class UpdateMemoItemUseCaseModel(
    val id: ItemId,
    val checked: Boolean,
    val content: ItemContent,
) {
    companion object {
        fun UpdateMemoItemUseCaseModel.toDomain() = Item.reconstruct(
            id = id,
            checked = checked,
            content = content,
        )
    }
}
