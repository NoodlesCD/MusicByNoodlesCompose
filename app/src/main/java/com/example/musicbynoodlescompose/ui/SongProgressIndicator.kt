package com.example.musicbynoodlescompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SongProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(if(progress.isNaN()) 0f else progress)
                .background(progressColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}