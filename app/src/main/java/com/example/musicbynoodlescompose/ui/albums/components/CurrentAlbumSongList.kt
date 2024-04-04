package com.example.musicbynoodlescompose.ui.albums.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Song
import com.example.musicbynoodlescompose.ui.albums.currentAlbumMenuItems
import com.example.musicbynoodlescompose.ui.misc.ListDropdownMenu

@Composable
fun CurrentAlbumSongList(
    album: Album,
    onSongSelected: (index: Int) -> Unit,
    addToQueue: (song: Song) -> Unit,
    addSongToPlaylist: (song: Song) -> Unit,
    onArtistSelected: (artistId: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        itemsIndexed(album.songs) { index, song ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable(
                        onClick = {
                            onSongSelected(index)
                        }
                    )
            ) {
                Text(
                    text = (index + 1).toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.width(30.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                ListDropdownMenu(
                    menuItems = currentAlbumMenuItems(
                        addToQueue = { addToQueue(song) },
                        addSongToPlaylist = { addSongToPlaylist(song) },
                        onArtistSelected = { onArtistSelected(song.artistId) }
                    )
                )
            }
            if (index != album.songs.size - 1) {
                Divider(
                    Modifier
                        .padding(50.dp, 0.dp, 15.dp, 0.dp)
                )
            }
        }
    }
}