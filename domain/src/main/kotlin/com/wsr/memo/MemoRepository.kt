package com.wsr.memo

import com.wsr.exception.DomainException
import com.wsr.result.ApiResult

interface MemoRepository {

    suspend fun insert(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun update(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun delete(memo: Memo): ApiResult<Unit, DomainException>
}
