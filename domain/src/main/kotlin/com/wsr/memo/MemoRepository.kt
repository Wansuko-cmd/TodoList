package com.wsr.memo

import com.wsr.exception.DomainException
import com.wsr.result.ApiResult
import kotlinx.coroutines.flow.Flow

interface MemoRepository {

    suspend fun getAll(): ApiResult<List<Memo>, DomainException>

    suspend fun getById(memoId: MemoId): Flow<ApiResult<Memo, DomainException>>

    suspend fun create(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun update(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun delete(memo: Memo): ApiResult<Unit, DomainException>
}
