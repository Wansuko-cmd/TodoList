package dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import entity.MemoWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Transaction
    @Query("SELECT * FROM MemoEntity")
    fun getMemos(): Flow<List<MemoWithItems>>

    @Transaction
    @Query("SELECT * FROM MemoEntity WHERE id=:id")
    fun getMemoById(id: String): Flow<MemoWithItems>
}
