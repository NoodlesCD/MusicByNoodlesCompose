package com.example.musicbynoodlescompose.ui.artists

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.example.musicbynoodlescompose.data.models.Artist
import com.example.musicbynoodlescompose.ui.library.Library
import com.example.musicbynoodlescompose.ui.library.LibraryItem

@Composable
fun ArtistsLibraryScreen(
    libraryItems: List<LibraryItem>,
    onArtistSelected: (artist: Artist) -> Unit,
    onSleepTimerSelected: () -> Unit,
    listState: LazyListState,
) {
    Library(
        title = "Artists",
        libraryItems = libraryItems,
        onRowSelected = { _, libraryItem -> onArtistSelected(libraryItemToArtist(libraryItem)) },
        onSleepTimerSelected = onSleepTimerSelected,
        listState = listState,
        dropdownMenuItems = null
    )
}

internal fun libraryItemToArtist(libraryItem: LibraryItem): Artist {
    return if (libraryItem is Artist) {
        libraryItem
    } else {
        Artist()
    }
}