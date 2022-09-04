package repository

import com.wsr.memo.MemoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindModule {
    @Binds
    fun bindMemoRepository(
        memoRepositoryImpl: MemoRepositoryImpl,
    ): MemoRepository
}
