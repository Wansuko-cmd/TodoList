package database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(context, AppDatabase::class.java, "app_db")
        .build()

    @Singleton
    @Provides
    fun provideMemoDao(db: AppDatabase) = db.memoDao()
}
