package api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MockMemoApiImpl @Inject constructor() : MemoApi {
    override suspend fun getAll(): Flow<List<MemoApiModel>> = flowOf(
        List(10) { index ->
            MemoApiModel(
                id = "id$index",
                title = "title$index",
                items = listOf(),
            )
        }
    )

    override suspend fun getById(memoId: String): Flow<MemoApiModel> = flowOf(
        MemoApiModel(
            id = memoId,
            title = "title",
            items = listOf(),
        )
    )
}
