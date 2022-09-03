package cqrs

import api.MemoApi
import api.MemoApiModel.Companion.toDomain
import com.wsr.FetchMemoByIdQueryService
import com.wsr.di.IODispatcher
import com.wsr.exception.DomainException
import com.wsr.memo.Memo
import com.wsr.memo.MemoId
import com.wsr.result.ApiResult
import com.wsr.result.consume
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import repository.runCatchDomainException
import javax.inject.Inject

class FetchMemoByIdQueryServiceImpl @Inject constructor(
    private val memoApi: MemoApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FetchMemoByIdQueryService {

    override val flow = MutableSharedFlow<ApiResult<Memo, DomainException>>(replay = 1)

    override suspend fun invoke(memoId: MemoId) {
        withContext(dispatcher) {
            runCatchDomainException {
                memoApi.getById(memoId.value).collect { data ->
                    flow.emit(ApiResult.Success(data.toDomain()))
                }
            }.consume(failure = { flow.emit(ApiResult.Failure(it)) })
        }
    }
}
