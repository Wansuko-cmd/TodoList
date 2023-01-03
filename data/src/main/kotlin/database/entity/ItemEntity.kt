package database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wsr.memo.Item
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = MemoEntity::class,
            parentColumns = ["id"],
            childColumns = ["memoId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("memoId")],
)
data class ItemEntity(
    @PrimaryKey val id: String,
    val memoId: String,
    val checked: Boolean,
    val content: String,
    val index: Int,
) {
    companion object {

        fun ItemEntity.toItem() = Item.reconstruct(
            id = ItemId(id),
            checked = checked,
            content = ItemContent(content),
        )

        fun from(items: List<Item>, memoId: MemoId) = items.mapIndexed { index, item ->
            ItemEntity(
                id = item.id.value,
                memoId = memoId.value,
                checked = item.checked,
                content = item.content.value,
                index = index,
            )
        }
    }
}
