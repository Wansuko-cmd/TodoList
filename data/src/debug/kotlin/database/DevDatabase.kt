package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import database.dao.MemoDao
import database.entity.ItemEntity
import database.entity.MemoEntity
import repository.room.InstantConverters

@Database(entities = [MemoEntity::class, ItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(InstantConverters::class)
abstract class DevDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}
