package com.example.musicbynoodlescompose.ui.currentlyPlaying.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.player.PlayerState

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