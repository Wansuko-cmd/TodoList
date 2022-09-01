package repository

import api.MemoApi
import api.MemoApiModel.Companion.toDomain
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
            runCatchDomainException { memoApi.getAll().map { it.toDomain() } }
        }

    override suspend fun getById(memoId: MemoId): ApiResult<Memo, DomainException> {
        TODO("Not yet implemented")
    }

    override suspend fun create(memo: Memo): ApiResult<Unit, DomainException> {
        TODO("Not yet implemented")
    }

    override suspend fun update(memo: Memo): ApiResult<Unit, DomainException> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(memo: Memo): ApiResult<Unit, DomainException> {
        TODO("Not yet implemented")
    }
}
