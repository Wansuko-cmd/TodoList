package api

import dao.MemoDao
import entity.ItemEntity
import entity.MemoEntity
import entity.MemoEntity.Companion.toApiModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoApiImpl @Inject constructor(
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
}
