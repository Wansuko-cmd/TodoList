package api

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import entity.ItemEntity
import entity.MemoEntity
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(context, AppDatabase::class.java, "dev_db")
        .build()
        .also { db -> memos.forEach { runBlocking { db.memoDao().insertMemo(it) } } }
        .also { db -> items.forEach { runBlocking { db.memoDao().insertItems(it) } } }

    @Singleton
    @Provides
    fun provideMemoDao(db: AppDatabase) = db.memoDao()
}

val memos = List(10) { index ->
    MemoEntity(id = "memoId$index", title = "title$index")
}

val items = List(10) { memoIndex ->
    List(10) { itemIndex ->
        ItemEntity(
            id = "itemId$memoIndex$itemIndex",
            memoId = "memoId$memoIndex",
            content = "content$itemIndex",
            index = itemIndex,
        )
    }
}.flatten()
