package api

class MockMemoApiImpl : MemoApi {
    override suspend fun getAll(): List<MemoApiModel> = listOf()
}
