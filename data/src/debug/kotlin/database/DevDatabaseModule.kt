package database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import database.dao.MemoDao
import database.entity.ItemEntity
import database.entity.MemoEntity
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DevDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): DevDatabase = Room
        .databaseBuilder(context, DevDatabase::class.java, "dev_db")
        .build()
        .also { db ->
            runBlocking {
                db.memoDao().getMemos().forEach { db.memoDao().deleteMemo(it.memo.id) }
            }
        }
        .also { db -> memos.forEach { runBlocking { db.memoDao().upsertMemo(it) } } }
        .also { db -> runBlocking { db.memoDao().upsertItems(items) } }

    @Singleton
    @Provides
    fun provideMemoDao(db: DevDatabase): MemoDao = db.memoDao()
}

private val memos = List(10) { index ->
    MemoEntity(id = "memoId$index", title = "title$index", accessedAt = Clock.System.now())
}

private val items = List(10) { memoIndex ->
    List(10) { itemIndex ->
        ItemEntity(
            id = "itemId$memoIndex$itemIndex",
            memoId = "memoId$memoIndex",
            checked = itemIndex % 2 == 0,
            content = "content$itemIndex",
            index = itemIndex,
        )
    }
}.flatten()
