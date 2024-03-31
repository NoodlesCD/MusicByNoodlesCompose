package com.example.musicbynoodlescompose.ui.albums

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Artist
import com.example.musicbynoodlescompose.data.Playlist
import com.example.musicbynoodlescompose.data.Song

class NavigationViewModel: ViewModel() {

    var currentAlbum by mutableStateOf(Album())
    var currentArtist by mutableStateOf(Artist())
    var currentPlaylist by mutableStateOf(Playlist())
    var songToAdd by mutableStateOf(listOf<Song>())
}