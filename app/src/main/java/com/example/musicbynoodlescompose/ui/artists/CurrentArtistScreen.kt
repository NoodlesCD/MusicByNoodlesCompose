package com.example.musicbynoodlescompose.ui.artists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Artist
import com.example.musicbynoodlescompose.data.models.Song
import com.example.musicbynoodlescompose.ui.artists.components.CurrentArtistAlbumList
import com.example.musicbynoodlescompose.ui.core.components.CurrentLibraryItemHeader
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem

@Composable
fun CurrentArtistScreen(
    artist: Artist,
    onAlbumSelected: (album: Album) -> Unit,
    addToQueue: (songs: List<Song>) -> Unit,
    addToPlaylist: (songs: List<Song>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 20.dp, 15.dp, 0.dp)
    ) {
        CurrentLibraryItemHeader(
            imageUri = artist.albums[0].albumUri,
            title = artist.name,
            description = if (artist.albums.size > 1) { "${artist.albums.size} albums" } else { "1 album" }
                    + " · "
                    + if (artist.songCount > 1) { "${artist.songCount} songs" } else { "1 song" },
        )
        CurrentArtistAlbumList(
            albums = artist.albums,
            onAlbumSelected = onAlbumSelected,
            addToPlaylist = addToPlaylist,
            addToQueue = addToQueue
        )
    }
}

internal fun currentArtistMenuItems(
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
        )
    )
}
