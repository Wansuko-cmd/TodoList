package com.wsr

sealed interface Path { val path: String }
sealed interface Root { val root: String }

object Route {
    object Memo : Root {
        override val root: String = "memo"

        object Index : Path { override val path = "$root/index" }

        object Show : Path {
            override val path: String = "$root/{memoId}"
            fun with(memoId: String) = "$root/$memoId"
        }
    }
}
