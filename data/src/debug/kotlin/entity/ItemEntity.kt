package entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import api.ItemApiModel

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = MemoEntity::class,
            parentColumns = ["id"],
            childColumns = ["memoId"],
            onDelete = ForeignKey.CASCADE,
        )
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
        fun ItemEntity.toApiModel() = ItemApiModel(
            id = id,
            checked,
            content = content,
        )
    }
}