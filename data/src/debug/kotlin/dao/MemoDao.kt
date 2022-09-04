package dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import entity.ItemEntity
import entity.MemoEntity
import entity.MemoWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Transaction
    @Query("SELECT * FROM memos")
    suspend fun getMemos(): List<MemoWithItems>

    @Transaction
    @Query("SELECT * FROM memos WHERE id=:id")
    suspend fun getMemoById(id: String): MemoWithItems

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMemo(memoEntity: MemoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertItems(items: List<ItemEntity>)
}
