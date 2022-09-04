package api

import kotlinx.coroutines.flow.Flow

interface MemoApi {
    suspend fun getAll(): Flow<List<MemoApiModel>>

    suspend fun getById(memoId: String): Flow<MemoApiModel>

    suspend fun upsert(memo: MemoApiModel)
}
