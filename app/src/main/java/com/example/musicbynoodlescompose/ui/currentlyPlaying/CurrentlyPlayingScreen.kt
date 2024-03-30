package com.example.musicbynoodlescompose.ui.currentlyPlaying

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.player.PlayerState
import com.example.musicbynoodlescompose.player.rememberMediaController
import kotlinx.coroutines.delay

@Composable
fun CurrentlyPlayingScreen(
    state: PlayerState?,
    onAction: (PlayerAction) -> Unit
) {
    val progress = remember { mutableLongStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            progress.longValue = state?.currentPosition ?: 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(
                data = state?.currentMediaItem?.mediaMetadata?.artworkUri,
            ),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .blur(10.dp)
                .alpha(0.3f),
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            Color.Transparent,
                            MaterialTheme.colorScheme.background,
                        )
                    )
                )
        ) {}
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(50.dp, 0.dp, 50.dp, 0.dp)
        ) {
            CurrentlyPlayingSongInfo(
                state = state
            )
            SongProgressSlider(
                state = state,
                progress = progress.longValue.toFloat(),
                onAction = onAction
            )
            SongControls(
                state = state,
                onAction = onAction,
                resetSlider = {
                    progress.longValue = 0
                }
            )
        }
    }
}

@Composable
fun CurrentlyPlayingSongInfo(
    state: PlayerState?
) {
    Image(
        painter = rememberImagePainter(
            data = state?.currentMediaItem?.mediaMetadata?.artworkUri,
            builder = {
                placeholder(R.drawable.artwork_placeholder)
                error(R.drawable.artwork_placeholder)
            }
        ),
        contentDescription = "Album artwork",
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .shadow(50.dp),
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
            text = state?.currentMediaItem?.mediaMetadata?.title.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = state?.currentMediaItem?.mediaMetadata?.artist.toString(),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun SongProgressSlider(
    state: PlayerState?,
    progress: Float,
    onAction: (PlayerAction) -> Unit
) {
    val mediaController by rememberMediaController()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
//        Slider(
//            value = 0F,
//            onValueChange = { selectedPosition ->
//                onAction(PlayerAction.Seek(selectedPosition.toLong()))
//            },
//            colors = SliderDefaults.colors(
//                thumbColor = MaterialTheme.colorScheme.onPrimary,
//                activeTrackColor = MaterialTheme.colorScheme.onPrimary
//            ),
//            valueRange = 0f..(state?.duration ?: 0).toFloat()
//        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = timeLabel(progress.toLong()),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = timeLabel(mediaController?.duration ?: 999),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun SongControls(
    state: PlayerState?,
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
            painter = if (state?.isPlaying == true) {
                painterResource(id = R.drawable.controls_pause)
            } else {
                painterResource(id = R.drawable.controls_play)
            },
            contentDescription = "Play",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.clickable (
                onClick = {
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