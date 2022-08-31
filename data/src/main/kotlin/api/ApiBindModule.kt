package api

import com.ace.c.android.utils.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ApiBindModule {
    @Binds
    fun bindMemoApi(@ApiFlavor(BuildConfig.BUILD_TYPE) impl: MemoApi): MemoApi
}