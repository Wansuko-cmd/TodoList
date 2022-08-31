package api

interface MemoApi {
    suspend fun getAll(): List<MemoApiModel>
}