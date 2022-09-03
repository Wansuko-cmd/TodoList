package api

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ApiBindModule {
    @Singleton
    @Binds
    fun bindMemoApi(impl: MemoApiImpl): MemoApi
}
