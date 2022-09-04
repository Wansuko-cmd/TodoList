package repository.room

import api.MemoApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RoomApiBindModule {
    @Singleton
    @Binds
    fun bindMemoApi(impl: RoomMemoApiImpl): MemoApi
}
