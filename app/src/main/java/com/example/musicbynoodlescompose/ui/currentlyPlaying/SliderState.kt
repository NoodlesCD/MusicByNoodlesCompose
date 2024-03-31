package com.example.musicbynoodlescompose.ui.currentlyPlaying

import androidx.core.net.toUri
import com.example.musicbynoodlescompose.data.Song

data class SliderState(
    val progress: Long = 0,
    val sliderPosition: Long = 0,
    val duration: Long = 0,
    val linearProgress: Float = 0f,
)
