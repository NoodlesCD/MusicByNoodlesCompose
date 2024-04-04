package com.example.musicbynoodlescompose.ui.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.musicbynoodlescompose.ui.library.Library
import com.example.musicbynoodlescompose.ui.library.LibraryItem
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem
import kotlinx.coroutines.CoroutineScope

/**
 * The actual list in the [Library] component which displays a list of [LibraryItem]
 *
 * @param listState State of the list. Can be updated by the [LibraryTopBar] component.
 * @param libraryItemList A list of [LibraryItem] to display in the UI.
 * @param scrollbarChars A list of characters to be displayed in the scrollbar, based on the list.
 */
@Composable
fun LibraryList(
    listState: LazyListState,
    libraryItemList: List<LibraryItem>,
    scrollbarChars: MutableMap<Char, Int>,
    coroutineScope: CoroutineScope,
    onRowSelected: (index: Int, libraryItem: LibraryItem) -> Unit,
    dropdownMenuItems: (libraryItem: LibraryItem) -> List<ListMenuItem>?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, end = 15.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(start = 15.dp, end = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            itemsIndexed(libraryItemList) { index, libraryItem ->
                LibraryListRow(
                    rowImage = libraryItem.rowImage,
                    primaryText = libraryItem.primaryText,
                    secondaryText = libraryItem.secondaryText,
                    isFinalRow = (index == libraryItemList.size - 1),
                    onRowSelected = { onRowSelected(index, libraryItem) },
                    dropdownMenuItems = dropdownMenuItems(libraryItem)
                )
            }
        }
        AlphabeticalScrollbar(
            scrollbarChars = scrollbarChars,
            coroutineScope = coroutineScope,
            listState = listState
        )
    }
}