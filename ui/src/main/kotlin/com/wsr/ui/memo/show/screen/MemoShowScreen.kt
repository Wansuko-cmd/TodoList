package com.wsr.ui.memo.show.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.component.MemoShowItemTile
import com.wsr.ui.memo.show.viewmodel.MemoShowViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoShowViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MemoShowScreen(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
        onChecked = viewModel::changeItemChecked,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    navController: NavHostController,
    onChecked: (itemId: String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MemoShowTopBar(
                navController = navController,
                memoTitle = uiState.title,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {

            // TODO:いい感じのComposableを入れることで一番上の要素をクリックしたときの挙動修正
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(uiState.items, key = { it.id }) {
                MemoShowItemTile(
                    modifier = Modifier.animateItemPlacement(),
                    itemUiState = it,
                    onChecked = onChecked,
                )
            }
        }
    }
}
