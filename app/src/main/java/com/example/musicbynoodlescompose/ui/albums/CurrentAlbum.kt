package com.example.musicbynoodlescompose.ui.albums

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Song

@Composable
fun CurrentAlbum(
    album: Album,
    onSongSelected: (index: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 20.dp, 15.dp, 0.dp)
    ) {
        CurrentAlbumHeader(
            album = album
        )
        CurrentAlbumList(
            album = album,
            onSongSelected = onSongSelected
        )
    }
}

@Composable
fun CurrentAlbumHeader(
    album: Album
) {
    Row(
        modifier = Modifier
            .padding(15.dp, 15.dp, 15.dp, 15.dp),
        verticalAlignment = Alignment.CenterVertically
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
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = album.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = album.artist,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun CurrentAlbumList(
    album: Album,
    onSongSelected: (index: Int) -> Unit
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
                    .clickable(
                        onClick = {
                            onSongSelected(index)
                        }
                    )
            ) {
                var isMenuExpanded by remember { mutableStateOf(false) }

                Text(
                    text = (index + 1).toString(),
                    Modifier.width(40.dp)
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
//                    Image(
//                        painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_menu_overflow_material),
//                        contentDescription = null
//                    )
//                    DropdownMenu(
//                        expanded = isMenuExpanded,
//                        onDismissRequest = { isMenuExpanded = false }
//                    ) {
//                        DropdownMenuItem(
//                            text = { "Add to queue" },
//                            onClick = { /*TODO*/ }
//                        )
//                        DropdownMenuItem(
//                            text = { "Add to playlist" },
//                            onClick = { /*TODO*/ }
//                        )
//                        DropdownMenuItem(
//                            text = { "View artist" },
//                            onClick = { /*TODO*/ }
//                        )
//                    }
            }
            if (index != album.songs.size - 1) {
                Divider(
                    Modifier
                        .padding(40.dp, 0.dp, 0.dp, 0.dp)
                )
            }
        }
    }
}