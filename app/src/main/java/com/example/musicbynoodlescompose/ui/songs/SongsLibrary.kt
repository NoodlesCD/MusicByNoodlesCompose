package com.example.musicbynoodlescompose.ui.songs

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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.TopBar
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.misc.ListMenu
import com.example.musicbynoodlescompose.ui.misc.ListMenuItem

@Composable
fun SongsLibrary(
    songsLibrary: List<Song>,
    onSongSelected: (index: Int) -> Unit,
    onAlbumSelected: (albumId: Long) -> Unit,
    onArtistSelected: (artistId: Long) -> Unit,
    addSongToPlaylist: (song: Song) -> Unit,
    addToQueue: (song: Song) -> Unit,
    listState: LazyListState,
) {

    var searchValue by remember { mutableStateOf("") }
    var searchedList by remember { mutableStateOf(songsLibrary) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = "Songs",
            listState = listState,
            searchValue = searchValue,
            onValueChange = {
                searchValue = it
                searchedList = songsLibrary.filter { song ->
                    song.title.lowercase().contains(searchValue.lowercase())
                }
            }
        )
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 10.dp, end = 15.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            itemsIndexed(searchedList) { index, song ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(15.dp, 0.dp, 15.dp, 0.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onSongSelected(index)
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
                    ListMenu(
                        menuItems = songLibraryMenuItems(
                            onArtistSelected = { onArtistSelected(song.artistId) },
                            onAlbumSelected = { onAlbumSelected(song.albumId) },
                            addSongToPlaylist = { addSongToPlaylist(song) },
                            addToQueue = { addToQueue(song) }
                        )
                    )

                }
                if (index != songsLibrary.size - 1) {
                    Divider(
                        Modifier
                            .padding(15.dp, 0.dp)
                    )
                }
            }
        }
    }
}


fun songLibraryMenuItems(
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