package com.example.musicbynoodlescompose.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Artist
import com.example.musicbynoodlescompose.data.models.Playlist
import com.example.musicbynoodlescompose.data.models.Song

class NavigationViewModel: ViewModel() {

    var currentAlbum by mutableStateOf(Album())
    var currentArtist by mutableStateOf(Artist())
    var currentPlaylist by mutableStateOf(Playlist())
    var songToAdd by mutableStateOf(listOf<Song>())
}