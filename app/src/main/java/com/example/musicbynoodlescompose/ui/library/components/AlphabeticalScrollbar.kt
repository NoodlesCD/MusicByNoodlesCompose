package com.example.musicbynoodlescompose.ui.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun AlphabeticalScrollbar(
    scrollbarChars: MutableMap<Char, Int>,
    coroutineScope: CoroutineScope,
    listState: LazyListState
) {
    val offsets: MutableMap<Char, Float> = remember { mutableStateMapOf<Char, Float>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(5.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val selectedChar = offsets
                        .mapValues { abs(it.value - offset.y) }
                        .entries
                        .minByOrNull { it.value }
                        ?.key

                    if (selectedChar != null) {
                        coroutineScope.launch {
                            listState.scrollToItem(scrollbarChars[selectedChar]!!)
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, _ ->
                    val selectedChar = offsets
                        .mapValues { abs(it.value - change.position.y) }
                        .entries
                        .minByOrNull { it.value }
                        ?.key

                    if (selectedChar != null) {
                        coroutineScope.launch {
                            listState.scrollToItem(scrollbarChars[selectedChar]!!)
                        }
                    }
                }
            },
    ) {

        for ((character, _) in scrollbarChars) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    //.onSizeChanged {  }
                    .onGloballyPositioned {
                        offsets[character] = it.boundsInParent().center.y
                    }
            ) {
                Text(
                    text = character.toString(),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}