package repository

import com.wsr.FetchAllMemoQueryService
import com.wsr.FetchMemoByIdQueryService
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

    @Binds
    fun bindFetchMemoByIdQueryService(
        fetchMemoByIdQueryServiceImpl: FetchMemoByIdQueryServiceImpl,
    ): FetchMemoByIdQueryService

    @Binds
    fun bindFetchAllMemoQueryService(
        fetchAllMemoQueryServiceImpl: FetchAllMemoQueryServiceImpl,
    ): FetchAllMemoQueryService
}
