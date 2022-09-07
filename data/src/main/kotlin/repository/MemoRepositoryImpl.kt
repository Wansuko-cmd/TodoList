package repository

import api.MemoApi
import api.MemoApiModel
import api.MemoApiModel.Companion.toMemo
import com.wsr.di.IODispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.memo.MemoRepository
import com.wsr.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoApi: MemoApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MemoRepository {
    override suspend fun getAll(): ApiResult<List<Memo>, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoApi.getAll().map { it.toMemo() }
            }
        }

    override suspend fun getById(memoId: MemoId): ApiResult<Memo, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoApi.getById(memoId.value).toMemo()
            }
        }

    override suspend fun upsert(memo: Memo): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoApi.upsert(MemoApiModel.from(memo))
            }
        }

    override suspend fun delete(id: MemoId): ApiResult<Unit, DomainException> =
        withContext(dispatcher) {
            runCatchDomainException {
                memoApi.delete(id.value)
            }
        }
}
