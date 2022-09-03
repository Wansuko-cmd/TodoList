package com.wsr.memo

import com.wsr.exception.DomainException
import com.wsr.result.ApiResult
import kotlinx.coroutines.flow.Flow

interface MemoRepository {

    suspend fun create(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun update(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun delete(memo: Memo): ApiResult<Unit, DomainException>
}
