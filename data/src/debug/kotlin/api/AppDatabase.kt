package api

import androidx.room.Database
import androidx.room.RoomDatabase
import dao.MemoDao
import entity.ItemEntity
import entity.MemoEntity

@Database(entities = [MemoEntity::class, ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}