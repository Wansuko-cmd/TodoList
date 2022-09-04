package api

import javax.inject.Inject

class MockMemoApiImpl @Inject constructor() : MemoApi {
    override suspend fun getAll(): List<MemoApiModel> = List(10) { memoIndex ->
        MemoApiModel(
            id = "id$memoIndex",
            title = "title$memoIndex",
            items = List(10) { itemIndex ->
                ItemApiModel(
                    id = "itemId$memoIndex$itemIndex",
                    checked = itemIndex % 2 == 0,
                    content = "content$itemIndex",
                )
            },
        )
    }

    override suspend fun getById(memoId: String): MemoApiModel =
        MemoApiModel(
            id = memoId,
            title = "title",
            items = List(10) { itemIndex ->
                ItemApiModel(
                    id = "itemId$memoId$itemIndex",
                    checked = itemIndex % 2 == 0,
                    content = "content$itemIndex",
                )
            },
        )

    override suspend fun upsert(memo: MemoApiModel) {
    }
}
