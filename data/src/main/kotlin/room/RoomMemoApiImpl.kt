package repository.room

import api.MemoApi
import api.MemoApiModel
import repository.room.dao.MemoDao
import repository.room.entity.ItemEntity
import repository.room.entity.MemoEntity
import repository.room.entity.MemoEntity.Companion.toApiModel
import javax.inject.Inject

class RoomMemoApiImpl @Inject constructor(
    private val memoDao: MemoDao,
) : MemoApi {
    override suspend fun getAll(): List<MemoApiModel> =
        memoDao.getMemos().map { (memo, items) ->
            memo.toApiModel(items)
        }

    override suspend fun getById(memoId: String): MemoApiModel =
        memoDao.getMemoById(memoId).let { (memo, items) ->
            memo.toApiModel(items)
        }

    override suspend fun upsert(memo: MemoApiModel) {
        memoDao.upsertMemo(MemoEntity.from(memo))
        memo.items.mapIndexed { index, item ->
            ItemEntity.from(item, memo.id, index)
        }.let { memoDao.upsertItems(it) }
    }

    override suspend fun delete(memoId: String) {

    }
}
