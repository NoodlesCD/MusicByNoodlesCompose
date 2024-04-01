package com.example.musicbynoodlescompose.ui.songs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.TopBar
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.misc.ListMenu
import com.example.musicbynoodlescompose.ui.misc.ListMenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SongsLibrary(
    songsLibrary: List<Song>,
    onSongSelected: (index: Int) -> Unit,
    onAlbumSelected: (albumId: Long) -> Unit,
    onArtistSelected: (artistId: Long) -> Unit,
    addSongToPlaylist: (song: Song) -> Unit,
    addToQueue: (song: Song) -> Unit,
    onSleepTimerSelected: () -> Unit,
    listState: LazyListState,
) {
    val coroutineScope = rememberCoroutineScope()
    var searchValue by remember { mutableStateOf("") }
    var searchedList by remember { mutableStateOf(songsLibrary) }

    val scrollerCharacters = remember(searchedList) {
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
                itemsIndexed(searchedList) { index, song ->
                    LibraryListRow(
                        rowImage = song.imageUri,
                        primaryText = song.title,
                        secondaryText = song.artist,
                        isFinalRow = (index == searchedList.size - 1),
                        onRowSelected = { onSongSelected(songsLibrary.indexOf(song)) },
                        dropdownMenuItems = songLibraryMenuItems(
                            onArtistSelected = { onArtistSelected(song.artistId) },
                            onAlbumSelected = { onAlbumSelected(song.albumId) },
                            addSongToPlaylist = { addSongToPlaylist(song) },
                            addToQueue = { addToQueue(song) }
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

@Composable
fun ListScroller(
    scrollerCharacters: MutableMap<Char, Int>,
    coroutineScope: CoroutineScope,
    listState: LazyListState
) {
    var containerPositionOffset by remember { mutableFloatStateOf(0F) }
    val offsets = remember { mutableStateMapOf<Char, Float>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(5.dp),
    ) {
        for (character in scrollerCharacters) {
            Text(
                text = character.key.toString(),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            coroutineScope.launch {
                                listState.scrollToItem(character.value)
                            }
                        }
                    )
            )
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



@Composable
fun LibraryListRow(
    rowImage: String,
    primaryText: String,
    secondaryText: String,
    isFinalRow: Boolean,
    dropdownMenuItems: List<ListMenuItem>? = null,
    onRowSelected: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(65.dp)
            .padding(15.dp, 0.dp, 15.dp, 0.dp)
            .fillMaxWidth()
            .clickable(
                onClick = onRowSelected
            )
    ) {
        Image(
            contentDescription = "Album artwork",
            painter = rememberImagePainter(
                data = rowImage,
                builder = {
                    placeholder(R.drawable.artwork_placeholder)
                    error(R.drawable.artwork_placeholder)
                }
            ),
            modifier = Modifier
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
                text = primaryText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = secondaryText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (dropdownMenuItems != null) {
            ListMenu(
                menuItems = dropdownMenuItems
            )
        }
    }
    if (!isFinalRow) {
        Divider(
            Modifier
                .padding(15.dp, 0.dp)
        )
    }
}