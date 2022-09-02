package com.wsr.ui.memo.show.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@Composable
fun memoShowViewModel(memoId: String): MemoShowViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MemoShowViewModelFactoryProvider::class.java,
    ).memoShowViewModelFactory()
    return viewModel(factory = provideFactory(factory, memoId))
}

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MemoShowViewModelFactoryProvider {
    fun memoShowViewModelFactory(): MemoShowViewModelAssistedFactory
}

@AssistedFactory
interface MemoShowViewModelAssistedFactory {
    fun create(
        @Assisted("memoId") memoId: String,
    ): MemoShowViewModel
}

@Suppress("UNCHECKED_CAST")
private fun provideFactory(
    assistedFactory: MemoShowViewModelAssistedFactory,
    memoId: String,
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        assistedFactory.create(memoId) as T
}
