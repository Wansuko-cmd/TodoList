package api

import dao.MemoDao
import entity.MemoEntity.Companion.toApiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoApiImpl @Inject constructor(
    private val memoDao: MemoDao,
) : MemoApi {
    override suspend fun getAll(): Flow<List<MemoApiModel>> =
        memoDao.getMemos().map { data ->
            data.map { (memo, items) ->
                memo.toApiModel(items)
            }
        }

    override suspend fun getById(memoId: String): Flow<MemoApiModel> =
        memoDao.getMemoById(memoId).map { (memo, items) ->
            memo.toApiModel(items)
        }
}
