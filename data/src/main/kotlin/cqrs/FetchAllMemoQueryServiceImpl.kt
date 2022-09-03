package cqrs

import api.MemoApi
import api.MemoApiModel.Companion.toDomain
import com.wsr.FetchAllMemoQueryService
import com.wsr.di.IODispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.Memo
import com.wsr.result.ApiResult
import com.wsr.result.consume
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import repository.runCatchDomainException
import javax.inject.Inject

class FetchAllMemoQueryServiceImpl @Inject constructor(
    private val memoApi: MemoApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FetchAllMemoQueryService {
    override val flow = MutableSharedFlow<ApiResult<List<Memo>, DomainException>>(replay = 1)

    override suspend fun invoke() {
        withContext(dispatcher) {
            runCatchDomainException {
                memoApi.getAll().collect { data ->
                    flow.emit(ApiResult.Success(data.map { it.toDomain() }))
                }
            }.consume(failure = { flow.emit(ApiResult.Failure(it)) })
        }
    }
}
