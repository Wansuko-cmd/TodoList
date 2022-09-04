package com.wsr.memo

import com.wsr.exception.DomainException
import com.wsr.result.ApiResult

interface MemoRepository {

    suspend fun upsert(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun delete(memo: Memo): ApiResult<Unit, DomainException>
}
