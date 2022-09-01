package api

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface MockApiBindModule {
    @ApiFlavor(ApiFlavor.MOCK)
    @Singleton
    @Binds
    fun bindMemoApi(mockMemoApiImpl: MockMemoApiImpl): MemoApi
}
