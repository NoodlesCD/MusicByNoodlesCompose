package com.example.musicbynoodlescompose.ui.albums

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.misc.ListMenu
import com.example.musicbynoodlescompose.ui.misc.ListMenuItem

@Composable
fun CurrentAlbum(
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
        MusicListHeader(
            imageUri = album.albumUri,
            title = album.title,
            description = album.artist
        )
        CurrentAlbumList(
            album = album,
            onSongSelected = onSongSelected,
            addToQueue = addToQueue,
            addSongToPlaylist = addSongToPlaylist,
            onArtistSelected = onArtistSelected
        )
    }
}

@Composable
fun CurrentAlbumList(
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
                ListMenu(
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

fun currentAlbumMenuItems(
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




@Composable
fun MusicListHeader(
    imageUri: Uri,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .padding(15.dp, 15.dp, 15.dp, 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUri,
                builder = {
                    placeholder(R.drawable.artwork_placeholder)
                    error(R.drawable.artwork_placeholder)
                }
            ),
            contentDescription = "",
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}