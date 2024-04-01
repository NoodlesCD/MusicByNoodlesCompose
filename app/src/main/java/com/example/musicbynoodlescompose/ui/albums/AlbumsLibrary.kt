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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.TopBar
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.misc.ListMenu
import com.example.musicbynoodlescompose.ui.misc.ListMenuItem
import com.example.musicbynoodlescompose.ui.songs.LibraryListRow
import com.example.musicbynoodlescompose.ui.songs.ListScroller
import com.example.musicbynoodlescompose.ui.songs.songLibraryMenuItems

@Composable
fun AlbumsLibrary(
    albumsLibrary: List<Album> = emptyList(),
    onAlbumSelected: (album: Album) -> Unit,
    onArtistSelected: (artistId: Long) -> Unit,
    addToPlaylist: (songs: List<Song>) -> Unit,
    addToQueue: (songs: List<Song>) -> Unit,
    onSleepTimerSelected: () -> Unit,
    listState: LazyListState,
) {
    var searchValue by remember { mutableStateOf("") }
    var searchedList by remember { mutableStateOf(albumsLibrary) }
    var scrollerCharacters = remember(searchedList) {
        val characters = mutableMapOf<Char, Int>()
        var foundFirstDigit = false
        for (i in searchedList.indices) {
            if (searchedList[i].title[0].isDigit() && foundFirstDigit) continue
            if (characters[searchedList[i].title[0].uppercaseChar()] == null ) {
                characters[searchedList[i].title[0].uppercaseChar()] = i
            }
            if (searchedList[i].title[0].isDigit()) foundFirstDigit = true
        }
        return@remember characters
    }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = "Albums",
            listState = listState,
            searchValue = searchValue,
            onValueChange = {
                searchValue = it
                searchedList = albumsLibrary.filter { album ->
                    album.title.lowercase().contains(searchValue.lowercase())
                }
            },
            onSleepTimerSelected = onSleepTimerSelected
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, end = 15.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp, end = 15.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                itemsIndexed(searchedList) { index, album ->
                    LibraryListRow(
                        rowImage = album.albumUri,
                        primaryText = album.title,
                        secondaryText = album.artist,
                        isFinalRow = (index == searchedList.size - 1),
                        onRowSelected = { onAlbumSelected(album) },
                        dropdownMenuItems = albumsLibraryMenuItems(
                            onArtistSelected = { onArtistSelected(album.artistId) },
                            addToPlaylist = { addToPlaylist(album.songs) },
                            addToQueue = { addToQueue(album.songs) }
                        )
                    )
                }
            }
            ListScroller(
                scrollerCharacters = scrollerCharacters,
                coroutineScope = coroutineScope,
                listState = listState
            )
        }
    }
}

fun albumsLibraryMenuItems(
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
