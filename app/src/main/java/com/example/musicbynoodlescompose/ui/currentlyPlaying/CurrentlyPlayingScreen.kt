package com.example.musicbynoodlescompose.ui.currentlyPlaying

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.player.PlayerAction

@Composable
fun CurrentlyPlayingScreen(
    state: CurrentlyPlayingState,
    resetSlider: () -> Unit,
    onAction: (PlayerAction) -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp, 0.dp, 50.dp, 130.dp)
        ) {
            CurrentlyPlayingSongInfo(
                state = state
            )
            SongProgressSlider(
                state = state,
                onAction = onAction
            )
            SongControls(
                state = state,
                resetSlider = resetSlider,
                onAction = onAction
            )
        }
    }
}

@Composable
fun CurrentlyPlayingSongInfo(
    state: CurrentlyPlayingState
) {
    Image(
        painter = rememberImagePainter(
            data = state.song.imageUri,
            builder = {
                placeholder(R.drawable.artwork_placeholder)
                error(R.drawable.artwork_placeholder)
            }
        ),
        contentDescription = "Album artwork",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )

    Column(
        Modifier
            .height(90.dp)
            .padding(0.dp, 40.dp, 0.dp, 0.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.song.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = state.song.artist,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun SongProgressSlider(
    state: CurrentlyPlayingState,
    onAction: (PlayerAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
        Slider(
            value = state.sliderPosition.toFloat(),
            onValueChange = { selectedPosition ->
                onAction(PlayerAction.Seek(selectedPosition.toLong()))
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.onPrimary
            ),
            valueRange = 0f..state.song.duration.toFloat()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = timeLabel(state.progress),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = timeLabel(state.song.duration.toLong()),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun SongControls(
    state: CurrentlyPlayingState,
    resetSlider: () -> Unit,
    onAction: (PlayerAction) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(40.dp, 20.dp, 40.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.controls_previous),
            contentDescription = "Previous",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.clickable (
                onClick = { onAction(PlayerAction.Previous) }
            )
        )
        Image(
            painter = if (state.isPlaying) {
                painterResource(id = R.drawable.controls_pause)
            } else {
                painterResource(id = R.drawable.controls_play)
            },
            contentDescription = "Play",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.clickable (
                onClick = {
                    resetSlider()
                    onAction(PlayerAction.PlayPause)
                }
            )
        )
        Image(
            painter = painterResource(id = R.drawable.controls_next),
            contentDescription = "Forward",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.clickable (
                onClick = {
                    resetSlider()
                    onAction(PlayerAction.Next)
                }
            )
        )
    }
}

/** Generates a time label of mm:ss */
private fun timeLabel(time: Long): String {
    var label = ""
    val min = time / 1000 / 60
    val sec = time / 1000 % 60

    label = "$min:"
    if (sec < 10) label += "0"
    label += sec

    return label
}