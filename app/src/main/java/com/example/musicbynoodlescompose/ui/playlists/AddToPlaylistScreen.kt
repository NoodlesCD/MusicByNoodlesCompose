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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.data.Artist
import com.example.musicbynoodlescompose.data.Playlist
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.misc.ListMenu
import com.example.musicbynoodlescompose.ui.misc.ListMenuItem

@Composable
fun AddToPlaylistScreen(
    songsToAdd: List<Song>,
    playlistsLibrary: List<Playlist>,
    onAddToPlaylistSelected: (playlist: Playlist) -> Unit
){
    val openDialog = remember { mutableStateOf(false) }
    val playlistName = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, top = 10.dp, end = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(65.dp)
                .padding(15.dp, 0.dp, 15.dp, 0.dp)
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        openDialog.value = true
                    }
                )
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Create new playlist",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(45.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 0.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Create new playlist",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Divider(
            Modifier
                .padding(15.dp, 0.dp)
        )
        LazyColumn() {
            itemsIndexed(playlistsLibrary) { index, playlist ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(15.dp, 0.dp, 15.dp, 0.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onAddToPlaylistSelected(
                                    Playlist(
                                        playlist.title,
                                        playlist.songs + songsToAdd
                                    )
                                )
                            }
                        )
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
                        Modifier
                            .size(45.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(15.dp, 0.dp, 0.dp, 0.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = playlist.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (playlist.songs.size > 1) "${playlist.songs.size} songs" else "1 song",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (index != playlistsLibrary.size - 1) {
                    Divider(
                        Modifier
                            .padding(15.dp, 0.dp)
                    )
                }
            }
        }
    }

    when {
        openDialog.value -> {
            AlertDialog(
                title = { Text("Create new playlist") },
                text = {
                    OutlinedTextField(
                        value = playlistName.value,
                        onValueChange = { playlistName.value = it },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                },
                onDismissRequest = {
                    openDialog.value = false
                    playlistName.value = ""
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onAddToPlaylistSelected(Playlist(playlistName.value, songsToAdd))
                            openDialog.value = false
                            playlistName.value = ""
                        }
                    ) {
                        Text(
                            text = "Confirm",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            playlistName.value = ""
                        }
                    ) {
                        Text(
                            text = "Dismiss",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
            )
        }
    }
}