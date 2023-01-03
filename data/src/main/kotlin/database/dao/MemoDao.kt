package database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import database.entity.ItemEntity
import database.entity.MemoEntity
import repository.room.entity.MemoWithItems

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

    @Query("DELETE FROM memos WHERE id=:id")
    suspend fun deleteMemo(id: String)
}
