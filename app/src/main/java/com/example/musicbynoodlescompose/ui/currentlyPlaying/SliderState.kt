package com.example.musicbynoodlescompose.ui.currentlyPlaying

data class SliderState(
    val progress: Long = 0,
    val sliderPosition: Long = 0,
    val duration: Long = 0,
    val linearProgress: Float = 0f,
)
