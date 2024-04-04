package com.example.musicbynoodlescompose.ui.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.musicbynoodlescompose.data.models.Playlist
import com.example.musicbynoodlescompose.ui.LibraryListRow
import com.example.musicbynoodlescompose.ui.LibraryTopBar
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem

@Composable
fun PlaylistsLibrary(
    playlistsLibrary: List<Playlist>,
    onPlaylistSelected: (playlist: Playlist) -> Unit,
    onDeletePlaylist: (playlist: Playlist) -> Unit,
    onSleepTimerSelected: () -> Unit,
    listState: LazyListState,
){
    var searchValue by remember { mutableStateOf("") }
    var searchedList by remember { mutableStateOf(playlistsLibrary) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LibraryTopBar(
            title = "Playlists",
            listState = listState,
            searchValue = searchValue,
            onValueChange = {
                searchValue = it
                searchedList = playlistsLibrary.filter { playlist ->
                    playlist.title.lowercase().contains(searchValue.lowercase())
                }
            },
            onSleepTimerSelected = onSleepTimerSelected
        )
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 10.dp, end = 15.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            itemsIndexed(searchedList) { index, playlist ->
                LibraryListRow(
                    rowImage = playlist.songs[0].imageUri,
                    primaryText = playlist.title,
                    secondaryText = if (playlist.songs.size > 1) "${playlist.songs.size} songs" else "1 song",
                    isFinalRow = (index == searchedList.size - 1),
                    onRowSelected = { onPlaylistSelected(playlist) },
                    dropdownMenuItems = playlistLibraryMenuItems(
                        onDeletePlaylist = { onDeletePlaylist(playlist) }
                    )
                )
            }
        }
    }
}

fun playlistLibraryMenuItems(
    onDeletePlaylist: () -> Unit
): List<ListMenuItem> {
    return listOf(
        ListMenuItem(
            text = "Delete playlist",
            onClick = onDeletePlaylist
        )
    )
}