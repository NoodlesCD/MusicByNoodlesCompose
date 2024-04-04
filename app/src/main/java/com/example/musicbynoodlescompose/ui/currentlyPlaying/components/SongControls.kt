package com.example.musicbynoodlescompose.ui.currentlyPlaying.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.player.PlayerState

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