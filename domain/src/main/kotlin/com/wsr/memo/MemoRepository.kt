package com.wsr.memo

interface MemoRepository {

    suspend fun getAll(): List<Memo>

    suspend fun getById(memoId: MemoId): Memo

    suspend fun create(memo: Memo)

    suspend fun update(memo: Memo)

    suspend fun delete(memo: Memo)
}
