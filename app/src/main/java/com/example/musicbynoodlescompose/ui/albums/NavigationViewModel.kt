package com.example.musicbynoodlescompose.ui.albums

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Artist

class CurrentAlbumViewModel: ViewModel() {

    var currentAlbum by mutableStateOf(
        Album(
            title = "",
            artist = "",
            artistId = 0L,
            songs = mutableListOf(),
            albumUri = "".toUri()
        )
    )
}