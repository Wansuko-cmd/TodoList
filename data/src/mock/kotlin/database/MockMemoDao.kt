package database

import database.dao.MemoDao
import database.entity.ItemEntity
import database.entity.MemoEntity
import kotlinx.datetime.Clock
import repository.room.entity.MemoWithItems
import javax.inject.Inject

class MockMemoDao @Inject constructor() : MemoDao {
    override suspend fun getMemos(): List<MemoWithItems> memos

    override suspend fun getMemoById(id: String): MemoWithItems = memos.first { id == id }

    override suspend fun upsertMemo(memoEntity: MemoEntity) = Unit

    override suspend fun upsertItems(items: List<ItemEntity>) = Unit

    override suspend fun deleteMemo(id: String) = Unit
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
