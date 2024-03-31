package com.example.musicbynoodlescompose.ui.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Artist
import com.example.musicbynoodlescompose.data.Playlist
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.misc.ListMenu
import com.example.musicbynoodlescompose.ui.misc.ListMenuItem

@Composable
fun CurrentPlaylist(
    playlist: Playlist,
    onSongSelected: (playlist: List<Song>, index: Int) -> Unit,
    onArtistSelected: (artistId: Long) -> Unit,
    onAlbumSelected: (albumId: Long) -> Unit,
    addToQueue: (song: Song) -> Unit,
    removeFromPlaylist: (playlist: Playlist, index: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 20.dp, 15.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp, 15.dp, 15.dp, 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(
                    data = playlist.songs[0].imageUri,
                    builder = {
                        placeholder(R.drawable.artwork_placeholder)
                        error(R.drawable.artwork_placeholder)
                    }
                ),
                contentDescription = "Album artwork",
                modifier = Modifier.size(70.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Column(
                modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = playlist.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Text(
                    text = if (playlist.songs.size > 1) { "${playlist.songs.size} songs" } else { "1 song" },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            itemsIndexed(playlist.songs) { index, song ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(15.dp, 0.dp, 15.dp, 0.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onSongSelected(playlist.songs, index)
                            }
                        )
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = song.imageUri,
                            builder = {
                                placeholder(R.drawable.artwork_placeholder)
                                error(R.drawable.artwork_placeholder)
                            }
                        ),
                        contentDescription = "Album artwork",
                        Modifier.size(45.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(15.dp, 2.dp, 0.dp, 0.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = song.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = song.artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    ListMenu(menuItems = playlistMenuItems(
                        onArtistSelected = { onArtistSelected(song.artistId) },
                        onAlbumSelected = { onAlbumSelected(song.albumId) },
                        addToQueue = { addToQueue(song) },
                        removeFromPlaylist = { removeFromPlaylist(playlist, index) }
                    ))
                }
                if (index != playlist.songs.size - 1) {
                    Divider(
                        Modifier
                            .padding(20.dp, 0.dp)
                    )
                }
            }
        }
    }
}

fun playlistMenuItems(
    onArtistSelected: () -> Unit,
    onAlbumSelected: () -> Unit,
    addToQueue: () -> Unit,
    removeFromPlaylist: () -> Unit
): List<ListMenuItem> {
    return listOf(
        ListMenuItem(
            text = "Add to queue",
            onClick = { addToQueue() }
        ),
        ListMenuItem(
            text = "View album",
            onClick = { onAlbumSelected() }
        ),
        ListMenuItem(
            text = "View artist",
            onClick = { onArtistSelected() }
        ),
        ListMenuItem(
            text = "Remove from playlist",
            onClick = { removeFromPlaylist() }
        )
    )
}
