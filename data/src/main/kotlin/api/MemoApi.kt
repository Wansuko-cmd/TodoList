package api

import kotlinx.coroutines.flow.Flow

interface MemoApi {
    suspend fun getAll(): List<MemoApiModel>

    suspend fun getById(memoId: String): Flow<MemoApiModel>
}
