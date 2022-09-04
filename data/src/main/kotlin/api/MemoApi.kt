package api

interface MemoApi {
    suspend fun getAll(): List<MemoApiModel>

    suspend fun getById(memoId: String): MemoApiModel

    suspend fun upsert(memo: MemoApiModel)

    suspend fun delete(memoId: String)
}
