package com.example.musicbynoodlescompose.ui.currentlyPlaying.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.ui.currentlyPlaying.SliderState

@Composable
fun SongProgressSlider(
    sliderState: SliderState,
    setSlider: (position: Long) -> Unit,
    onAction: (PlayerAction) -> Unit
) {
    var sliderProgress by remember(sliderState.sliderPosition) { mutableFloatStateOf(sliderState.sliderPosition.toFloat()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
        Slider(
            value = sliderProgress,
            onValueChange = { selectedPosition ->
                sliderProgress = selectedPosition
                onAction(PlayerAction.Seek(selectedPosition.toLong()))
                setSlider(selectedPosition.toLong())
            },
            onValueChangeFinished = {
                sliderProgress = sliderState.sliderPosition.toFloat()
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.onPrimary,
                activeTrackColor = MaterialTheme.colorScheme.onPrimary
            ),
            valueRange = 0f..sliderState.duration.toFloat()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = timeLabel(sliderState.progress.toLong()),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = timeLabel(sliderState.duration),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/** Generates a time label of mm:ss */
internal fun timeLabel(time: Long): String {
    var label = ""
    val min = time / 1000 / 60
    val sec = time / 1000 % 60

    label = "$min:"
    if (sec < 10) label += "0"
    label += sec

    return label
}