package com.example.musicbynoodlescompose.ui.albums

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Song
import com.example.musicbynoodlescompose.ui.albums.components.CurrentAlbumSongList
import com.example.musicbynoodlescompose.ui.core.components.CurrentLibraryItemHeader
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem

@Composable
fun CurrentAlbumScreen(
    album: Album,
    onSongSelected: (index: Int) -> Unit,
    addToQueue: (song: Song) -> Unit,
    addSongToPlaylist: (song: Song) -> Unit,
    onArtistSelected: (artistId: Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 20.dp, 15.dp, 0.dp)
    ) {
        CurrentLibraryItemHeader(
            imageUri = album.albumUri,
            title = album.title,
            description = album.artist
        )
        CurrentAlbumSongList(
            album = album,
            onSongSelected = onSongSelected,
            addToQueue = addToQueue,
            addSongToPlaylist = addSongToPlaylist,
            onArtistSelected = onArtistSelected
        )
    }
}

internal fun currentAlbumMenuItems(
    addToQueue: () -> Unit,
    addSongToPlaylist: () -> Unit,
    onArtistSelected: () -> Unit
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
            text = "View artist",
            onClick = { onArtistSelected() }
        )
    )
}
