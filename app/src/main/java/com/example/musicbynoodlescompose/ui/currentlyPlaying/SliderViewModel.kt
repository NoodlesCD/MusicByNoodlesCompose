package com.example.musicbynoodlescompose.ui.currentlyPlaying

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.musicbynoodlescompose.ui.currentlyPlaying.SliderState

class SliderViewModel: ViewModel() {

    var state by mutableStateOf(SliderState())

    fun setSlider(position: Long) {
        state = state.copy(
            sliderPosition = position
        )
    }
}