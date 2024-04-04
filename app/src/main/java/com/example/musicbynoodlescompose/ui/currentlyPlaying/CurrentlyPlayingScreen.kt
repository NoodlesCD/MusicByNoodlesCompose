package com.example.musicbynoodlescompose.ui.currentlyPlaying

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.player.PlayerState
import com.example.musicbynoodlescompose.player.rememberMediaController
import com.example.musicbynoodlescompose.ui.currentlyPlaying.components.CurrentlyPlayingBackground
import com.example.musicbynoodlescompose.ui.currentlyPlaying.components.CurrentlyPlayingSongInfo
import com.example.musicbynoodlescompose.ui.currentlyPlaying.components.SongControls
import com.example.musicbynoodlescompose.ui.currentlyPlaying.components.SongProgressSlider
import kotlinx.coroutines.delay

@Composable
fun CurrentlyPlayingScreen(
    state: PlayerState?,
    sliderState: SliderState,
    onAction: (PlayerAction) -> Unit,
    setSlider: (position: Long) -> Unit
) {
    CurrentlyPlayingBackground(bgImage = state?.currentMediaItem?.mediaMetadata?.artworkUri)
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
                sliderState = sliderState,
                setSlider = setSlider,
                onAction = onAction
            )
            SongControls(
                state = state,
                onAction = onAction,
                resetSlider = { setSlider(0) }
            )
        }
    }
}