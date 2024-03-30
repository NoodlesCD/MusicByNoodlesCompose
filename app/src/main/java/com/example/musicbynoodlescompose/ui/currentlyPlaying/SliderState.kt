package com.example.musicbynoodlescompose.ui.currentlyPlaying

import android.net.Uri
import androidx.core.net.toUri
import com.example.musicbynoodlescompose.data.Song

data class CurrentlyPlayingState(
    val song: Song = Song(0L,
        "",
        "",
        "",
        0,
        0L,
        0L,
        0,
        "".toUri(),
        "".toUri()
    ),
    val progress: Long = 0,
    val sliderPosition: Long = 0,
    val isPlaying: Boolean = false
)
