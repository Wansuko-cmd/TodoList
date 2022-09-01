package com.wsr

import com.wsr.memo.MemoId

sealed interface Path { val path: String }
sealed interface Root { val root: String }

object Route {
    object Memo : Root {
        override val root: String = "memo"

        object Index : Path { override val path = "$root/index" }

        object Show : Path {
            override val path: String = "$root/{memo_Id}"
            fun with(memoId: MemoId) = "$root/${memoId.value}"
        }
    }
}
