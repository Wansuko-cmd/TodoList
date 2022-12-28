package com.wsr.memo

import com.wsr.exception.DomainException
import com.wsr.result.ApiResult
import kotlinx.coroutines.flow.Flow

interface MemoRepository {

    suspend fun getAll(): ApiResult<List<Memo>, DomainException>

    fun getAllFlow(): Flow<ApiResult<List<Memo>, DomainException>>

    suspend fun getById(memoId: MemoId): ApiResult<Memo, DomainException>

    fun getFlowById(memoId: MemoId): Flow<ApiResult<Memo, DomainException>>

    suspend fun upsert(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun delete(id: MemoId): ApiResult<Unit, DomainException>
}
