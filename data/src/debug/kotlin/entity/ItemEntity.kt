package entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import api.ItemApiModel

@Entity
data class ItemEntity(
    @PrimaryKey val id: String,
    val memoId: String,
    val content: String,
    val index: String,
) {
    companion object {
        fun ItemEntity.toApiModel() = ItemApiModel(
            id = id,
            content = content,
        )
    }
}