package com.example.musicbynoodlescompose.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem
import com.example.musicbynoodlescompose.ui.library.components.LibraryList
import com.example.musicbynoodlescompose.ui.library.components.LibraryTopBar

/**
 * A library component for displaying libraries of items such as Songs, Albums or Artists.
 *
 * @param title The title to be shown in the header of the library.
 * @param libraryItems The list of items to be generated in the UI.
 * @param dropdownMenuItems Additional options to be shown in a dropdown for each row of the list.
 * @param onRowSelected Action to be performed when an individual row of the list is selected.
 * @param onSleepTimerSelected Passes sleep timer up the Composable chain.
 * @param listState Current state of the list.
 */
@Composable
fun Library(
    title: String,
    libraryItems: List<LibraryItem>,
    dropdownMenuItems: ((libraryItem: LibraryItem) -> List<ListMenuItem>)?,
    onRowSelected: (index: Int, libraryItem: LibraryItem) -> Unit,
    onSleepTimerSelected: () -> Unit,
    listState: LazyListState,
) {
    val coroutineScope = rememberCoroutineScope()
    var searchValue by remember { mutableStateOf("") }
    var searchedList by remember { mutableStateOf(libraryItems) }

    val scrollerCharacters = remember(searchedList) {
        val characters = mutableMapOf<Char, Int>()
        var foundFirstDigit = false
        var foundFirstSymbol = false

        for (i in searchedList.indices) {
            if (!searchedList[i].searchParameter[0].isLetterOrDigit() && foundFirstSymbol) continue
            if (searchedList[i].searchParameter[0].isDigit() && foundFirstDigit) continue

            if (characters[searchedList[i].searchParameter[0].uppercaseChar()] == null ) {
                characters[searchedList[i].searchParameter[0].uppercaseChar()] = i
            }
            if (!searchedList[i].searchParameter[0].isLetterOrDigit()) foundFirstSymbol = true
            if (searchedList[i].searchParameter[0].isDigit()) foundFirstDigit = true
            if (searchedList[i].searchParameter[0] == 'Z') break
        }

        return@remember characters
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LibraryTopBar(
            title = title,
            listState = listState,
            searchValue = searchValue,
            onSleepTimerSelected = onSleepTimerSelected,
            onValueChange = {
                searchValue = it
                searchedList = libraryItems.filter { libraryItem ->
                    libraryItem.searchParameter.lowercase().contains(searchValue.lowercase())
                }
            }
        )
        LibraryList(
            listState = listState,
            libraryItemList = searchedList,
            scrollbarChars = scrollerCharacters,
            coroutineScope = coroutineScope,
            onRowSelected = { index, libraryItem -> onRowSelected(index, libraryItem) },
            dropdownMenuItems = { libraryItem ->
                if (dropdownMenuItems != null) dropdownMenuItems(libraryItem) else null
            }
        )
    }
}







