package com.wsr.memo

import com.wsr.exception.DomainException
import com.wsr.result.ApiResult

interface MemoRepository {

    suspend fun getAll(): ApiResult<List<Memo>, DomainException>

    suspend fun getById(memoId: MemoId): ApiResult<Memo, DomainException>

    suspend fun upsert(memo: Memo): ApiResult<Unit, DomainException>

    suspend fun delete(memo: Memo): ApiResult<Unit, DomainException>
}
