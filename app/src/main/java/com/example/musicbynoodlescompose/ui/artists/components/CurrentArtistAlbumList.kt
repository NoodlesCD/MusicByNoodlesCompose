package com.example.musicbynoodlescompose.ui.artists.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Song
import com.example.musicbynoodlescompose.ui.artists.currentArtistMenuItems
import com.example.musicbynoodlescompose.ui.misc.ListDropdownMenu

@Composable
fun CurrentArtistAlbumList(
    albums: List<Album>,
    onAlbumSelected: (album: Album) -> Unit,
    addToPlaylist: (songs: List<Song>) -> Unit,
    addToQueue: (songs: List<Song>) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        itemsIndexed(albums) { index, album ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(65.dp)
                    .padding(15.dp, 0.dp, 15.dp, 0.dp)
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onAlbumSelected(album) }
                    )
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = album.albumUri,
                        builder = {
                            placeholder(R.drawable.artwork_placeholder)
                            error(R.drawable.artwork_placeholder)
                        }
                    ),
                    contentDescription = "Album artwork",
                    Modifier
                        .size(45.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(15.dp, 2.dp, 0.dp, 0.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = album.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                ListDropdownMenu(
                    menuItems = currentArtistMenuItems(
                        addToPlaylist = { addToPlaylist(album.songs) },
                        addToQueue = { addToQueue(album.songs) }
                    ))
            }
            if (index != albums.size - 1) {
                Divider(
                    Modifier
                        .padding(20.dp, 0.dp)
                )
            }
        }
    }
}