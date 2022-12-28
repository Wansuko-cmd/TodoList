package repository

import com.wsr.di.IODispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.result.ApiResult
import database.dao.MemoDao
import database.entity.ItemEntity
import database.entity.MemoEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.room.entity.MemoWithItems.Companion.toMemo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MemoRepositoryImpl @Inject constructor(
    private val memoDao: MemoDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MemoRepository {
    override suspend fun getAll(): ApiResult<List<Memo>, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoDao.getMemos().map { it.toMemo() }
            }
        }

    override fun getAllFlow(): Flow<ApiResult<List<Memo>, DomainException>> =
        memoDao.getMemosFlow().map { data -> ApiResult.Success(data.map { it.toMemo() }) }

    override suspend fun getById(memoId: MemoId): ApiResult<Memo, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoDao.getMemoById(memoId.value).toMemo()
            }
        }

    override fun getFlowById(memoId: MemoId): Flow<ApiResult<Memo, DomainException>> =
        memoDao.getMemoFlowById(memoId.value).map { data -> ApiResult.Success(data.toMemo()) }

    override suspend fun upsert(memo: Memo): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoDao.upsertMemo(MemoEntity.from(memo = memo))
                memoDao.upsertItems(ItemEntity.from(items = memo.items, memoId = memo.id))
            }
        }

    override suspend fun delete(id: MemoId): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoDao.deleteMemo(id.value)
            }
        }
}
