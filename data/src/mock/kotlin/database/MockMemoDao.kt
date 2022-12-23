package database

import database.dao.MemoDao
import database.entity.ItemEntity
import database.entity.MemoEntity
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import repository.room.entity.MemoWithItems
import javax.inject.Inject

class MockMemoDao @Inject constructor() : MemoDao {
    override suspend fun getMemos(): List<MemoWithItems> = memos.also { delay(1000L) }

    override suspend fun getMemoById(id: String): MemoWithItems =
        memos.first { it.memo.id == id }.also { delay(1000L) }

    override suspend fun upsertMemo(memoEntity: MemoEntity) = Unit.also { delay(1000L) }

    override suspend fun upsertItems(items: List<ItemEntity>) = Unit.also { delay(1000L) }

    override suspend fun deleteMemo(id: String) = Unit.also { delay(1000L) }
}

val memos = List(10) { memoIndex ->
    MemoWithItems(
        memo = MemoEntity(
            id = "memoId$memoIndex",
            title = "title$memoIndex",
            accessedAt = Clock.System.now()
        ),
        items = List(10) { itemIndex ->
            ItemEntity(
                id = "itemId$memoIndex$itemIndex",
                memoId = "memoId$memoIndex",
                checked = itemIndex % 2 == 0,
                content = "content$itemIndex",
                index = itemIndex,
            )
        }
    )
}
