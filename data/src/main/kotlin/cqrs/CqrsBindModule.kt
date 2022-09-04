package repository.cqrs

import com.wsr.FetchAllMemoQueryService
import com.wsr.FetchMemoByIdQueryService
import cqrs.FetchAllMemoQueryServiceImpl
import cqrs.FetchMemoByIdQueryServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CqrsBindModule {
    @Binds
    fun bindFetchMemoByIdQueryService(
        fetchMemoByIdQueryServiceImpl: FetchMemoByIdQueryServiceImpl,
    ): FetchMemoByIdQueryService

    @Binds
    fun bindFetchAllMemoQueryService(
        fetchAllMemoQueryServiceImpl: FetchAllMemoQueryServiceImpl,
    ): FetchAllMemoQueryService
}
