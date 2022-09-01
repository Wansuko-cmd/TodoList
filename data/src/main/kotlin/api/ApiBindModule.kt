package api

import com.wsr.data.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ApiBindModule {
    @Binds
    fun bindMemoApi(@ApiFlavor(BuildConfig.FLAVOR_TYPE) impl: MemoApi): MemoApi
}
