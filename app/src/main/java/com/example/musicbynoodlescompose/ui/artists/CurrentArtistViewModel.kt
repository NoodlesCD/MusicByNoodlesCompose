package com.example.musicbynoodlescompose.ui.artists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.musicbynoodlescompose.data.Artist

class CurrentArtistViewModel: ViewModel() {

    var currentArtist by mutableStateOf(
        Artist(
            name = "",
            albums = mutableListOf(),
            songCount = 0
        )
    )

}