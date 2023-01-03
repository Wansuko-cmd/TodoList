package database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.memo.Memo
import kotlinx.datetime.Instant

@Entity(tableName = "memos")
data class MemoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val accessedAt: Instant,
) {
    companion object {
        fun from(memo: Memo): MemoEntity = MemoEntity(
            id = memo.id.value,
            title = memo.title.value,
            accessedAt = memo.accessedAt,
        )
    }
}
