package com.wsr

import com.wsr.memo.MemoId

sealed interface Route {
    val path: String

    object Index : Route { override val path = "memo/index" }

    object Show : Route {
        override val path: String = "memo/{memo_Id}"
        fun with(memoId: MemoId) = "memo/${memoId.value}"
    }
}
