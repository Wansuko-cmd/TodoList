package api

import javax.inject.Inject

class MockMemoApiImpl @Inject constructor() : MemoApi {
    override suspend fun getAll(): List<MemoApiModel> = List(10) { index ->
        MemoApiModel(
            id = "id$index",
            title = "title$index",
            items = listOf(),
        )
    }
}
