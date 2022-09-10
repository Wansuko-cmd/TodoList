package database

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import database.dao.MemoDao

@Module
@InstallIn(SingletonComponent::class)
interface MockDaoBindModule {
    @Binds
    fun bindMemoDao(mockMemoDao: MockMemoDao): MemoDao
}
