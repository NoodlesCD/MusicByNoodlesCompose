package com.example.musicbynoodlescompose.ui.albums

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Song
import com.example.musicbynoodlescompose.ui.library.Library
import com.example.musicbynoodlescompose.ui.library.LibraryItem
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem

@Composable
fun AlbumsLibraryScreen(
    libraryItems: List<LibraryItem>,
    actions: AlbumsLibraryActions,
    listState: LazyListState,
) {
    Library(
        title = "Albums",
        libraryItems = libraryItems,
        onRowSelected = { _, libraryItem -> actions.onViewAlbumSelected(libraryItemToAlbum(libraryItem)) },
        onSleepTimerSelected = actions.onSleepTimerSelected,
        listState = listState,
        dropdownMenuItems = { libraryItem ->
            val album = libraryItemToAlbum(libraryItem)
            return@Library albumsLibraryMenuItems(
                onArtistSelected = { actions.onViewArtistSelected(album.artistId) },
                addToPlaylist = { actions.onAddToPlaylistSelected(album.songs) },
                addToQueue = { actions.onAddToQueueSelected(album.songs) }
            )
        }
    )
}

data class AlbumsLibraryActions(
    val onViewAlbumSelected: (album: Album) -> Unit,
    val onViewArtistSelected: (artistId: Long) -> Unit,
    val onAddToPlaylistSelected: (songs: List<Song>) -> Unit,
    val onAddToQueueSelected: (songs: List<Song>) -> Unit,
    val onSleepTimerSelected: () -> Unit
)

internal fun libraryItemToAlbum(libraryItem: LibraryItem): Album {
    return if (libraryItem is Album) {
        libraryItem
    } else {
        Album()
    }
}

internal fun albumsLibraryMenuItems(
    onArtistSelected: () -> Unit,
    addToPlaylist: () -> Unit,
    addToQueue: () -> Unit,
): List<ListMenuItem> {
    return listOf(
        ListMenuItem(
            text = "Add to queue",
            onClick = { addToQueue() }
        ),
        ListMenuItem(
            text = "Add to playlist",
            onClick = { addToPlaylist() }
        ),
        ListMenuItem(
            text = "View artist",
            onClick = { onArtistSelected() }
        )
    )
}
