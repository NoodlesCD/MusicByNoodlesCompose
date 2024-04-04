package com.example.musicbynoodlescompose.ui.songs

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import com.example.musicbynoodlescompose.data.models.Song
import com.example.musicbynoodlescompose.ui.library.Library
import com.example.musicbynoodlescompose.ui.library.LibraryItem
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem

@Composable
fun SongsLibrary(
    libraryItems: List<LibraryItem>,
    actions: SongLibraryActions,
    listState: LazyListState,
) {
    Library(
        title = "Songs",
        libraryItems = libraryItems,
        onRowSelected = { index, _ -> actions.onPlaySongSelected(index) },
        onSleepTimerSelected = actions.onSleepTimerSelected,
        listState = listState,
        dropdownMenuItems = { libraryItem ->
            val song = libraryItemToSong(libraryItem)
            return@Library songLibraryMenuItems(
                onArtistSelected = { actions.onViewArtistSelected(song.artistId) },
                onAlbumSelected = { actions.onViewAlbumSelected(song.albumId) },
                addSongToPlaylist = { actions.onAddToPlaylistSelected(song) },
                addToQueue = { actions.onAddToQueueSelected(song) }
            )
        }
    )
}

data class SongLibraryActions(
    val onPlaySongSelected: (index: Int) -> Unit,
    val onViewAlbumSelected: (albumId: Long) -> Unit,
    val onViewArtistSelected: (artistId: Long) -> Unit,
    val onAddToPlaylistSelected: (song: Song) -> Unit,
    val onAddToQueueSelected: (song: Song) -> Unit,
    val onSleepTimerSelected: () -> Unit
)

internal fun libraryItemToSong(libraryItem: LibraryItem): Song {
    return if (libraryItem is Song) {
        libraryItem
    } else {
        Song()
    }
}

internal fun songLibraryMenuItems(
    onArtistSelected: () -> Unit,
    onAlbumSelected: () -> Unit,
    addSongToPlaylist: () -> Unit,
    addToQueue: () -> Unit,
): List<ListMenuItem> {
    return listOf(
        ListMenuItem(
            text = "Add to queue",
            onClick = { addToQueue() }
        ),
        ListMenuItem(
            text = "Add to playlist",
            onClick = { addSongToPlaylist() }
        ),
        ListMenuItem(
            text = "View album",
            onClick = { onAlbumSelected() }
        ),
        ListMenuItem(
            text = "View artist",
            onClick = { onArtistSelected() }
        )
    )
}