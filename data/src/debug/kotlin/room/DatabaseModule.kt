package room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import repository.room.entity.ItemEntity
import repository.room.entity.MemoEntity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(context, DevDatabase::class.java, "dev_db")
        .build()
        .also { db -> memos.forEach { runBlocking { db.memoDao().upsertMemo(it) } } }
        .also { db -> runBlocking { db.memoDao().upsertItems(items) } }

    @Singleton
    @Provides
    fun provideMemoDao(db: DevDatabase) = db.memoDao()
}

val memos = List(10) { index ->
    MemoEntity(id = "memoId$index", title = "title$index")
}

val items = List(10) { memoIndex ->
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
