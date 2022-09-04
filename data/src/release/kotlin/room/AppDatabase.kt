package room

import androidx.room.Database
import androidx.room.RoomDatabase
import repository.room.dao.MemoDao
import repository.room.entity.ItemEntity
import repository.room.entity.MemoEntity

@Database(entities = [MemoEntity::class, ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}
