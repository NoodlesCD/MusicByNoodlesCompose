package com.example.musicbynoodlescompose.ui.main.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.AdjLinearProgressIndicator
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.player.PlayerState
import com.example.musicbynoodlescompose.ui.SongProgressIndicator
import com.example.musicbynoodlescompose.ui.currentlyPlaying.SliderState

@Composable
fun CurrentlyPlayingBar(
    isVisible: Boolean,
    playerState: PlayerState?,
    sliderState: SliderState,
    navToCurrentlyPlayingScreen: () -> Unit,
    playPauseAction: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .animateContentSize(animationSpec = tween(durationMillis = 500))
            .fillMaxWidth()
            .height(if (isVisible) 70.dp else 0.dp)
            .padding(horizontal = 30.dp)
            .padding(top = 10.dp)
            .clickable(
                onClick = {
                    navToCurrentlyPlayingScreen()
                }
            ),
    ) {
        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(
                    data = playerState
                        ?.currentMediaItem
                        ?.mediaMetadata
                        ?.artworkUri,
                    builder = {
                        placeholder(R.drawable.artwork_placeholder)
                        error(R.drawable.artwork_placeholder)
                    }
                ),
                contentDescription = "Album artwork",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 10.dp)
                    .height(50.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = playerState
                            ?.currentMediaItem
                            ?.mediaMetadata
                            ?.title
                            .toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = playerState
                            ?.currentMediaItem
                            ?.mediaMetadata
                            ?.artist
                            .toString(),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                AdjLinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().height(2.dp),
                    progressColor = MaterialTheme.colorScheme.onPrimary,
                    backgroundColor = MaterialTheme.colorScheme.onSecondary,
                    tipColor = MaterialTheme.colorScheme.primary,
                    progress = sliderState.progress.toFloat(),
                    totalProgress = sliderState.duration.toFloat(),
                )


//                SongProgressIndicator(
//                    progress = sliderState.linearProgress,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(2.dp)
//                    //.alpha(if (visible) 1f else 0f)
//                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                imageVector = if (playerState?.isPlaying == true) {
                    Icons.Default.Pause
                } else {
                    Icons.Default.PlayArrow
                },
                contentDescription = "Play",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        onClick = {
                            playPauseAction()
                        }
                    )
            )
        }
    }
}