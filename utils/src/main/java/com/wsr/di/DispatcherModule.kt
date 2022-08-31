package com.wsr.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * DispatcherをHiltで扱えるようにするためのアノテーション
 * コンストラクタに出したDispatcherにこれらのアノテーションを入れることで代入されるようになる
 *
 * 参考：https://www.valueof.io/blog/injecting-coroutines-dispatchers-with-dagger
 */

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IODispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

/**
 * Dispatcherのインジェクトに利用するQualifierアノテーション
 * ビルド後にも存在してほしいが、Hilt以外で利用する予定はないためBINARYで定義
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IODispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher