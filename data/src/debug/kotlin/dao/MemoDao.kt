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
    fun getMemos(): Flow<List<MemoWithItems>>

    @Transaction
    @Query("SELECT * FROM memos WHERE id=:id")
    fun getMemoById(id: String): Flow<MemoWithItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memoEntity: MemoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)
}
