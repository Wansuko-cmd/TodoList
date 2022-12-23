package com.wsr.ui.memo.show.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wsr.common.composable.WithoutPaddingTextField
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.theme.LightBlue
import com.wsr.theme.LightPink
import com.wsr.ui.memo.show.MemoShowItemUiState

@Composable
fun MemoShowItemTile(
    modifier: Modifier = Modifier,
    itemUiState: MemoShowItemUiState,
    isDragging: Boolean,
    shouldFocus: Boolean,
    onChecked: (itemId: ItemId) -> Unit,
    onChangeContent: (itemId: ItemId, content: ItemContent) -> Unit,
) {
    val backgroundColor = when {
        isDragging -> LightPink
        itemUiState.checked -> Color.White
        else -> LightBlue
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(shouldFocus) {
        if (shouldFocus) focusRequester.requestFocus()
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = backgroundColor,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = itemUiState.checked, onCheckedChange = { onChecked(itemUiState.id) })
            WithoutPaddingTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = itemUiState.content.value,
                onValueChange = { onChangeContent(itemUiState.id, ItemContent(it)) },
                textStyle = MaterialTheme.typography.h5,
                enabled = !isDragging,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = backgroundColor,
                    unfocusedIndicatorColor = backgroundColor,
                    focusedIndicatorColor = backgroundColor,
                )
            )
        }
    }
}
