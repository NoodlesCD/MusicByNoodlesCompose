package com.example.musicbynoodlescompose.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.example.musicbynoodlescompose.data.Song

@Composable
fun rememberMediaController(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle //lifecycle of the owner, so we can dispose of when needed
): MutableState<MediaController?> {
    val appContext = LocalContext.current.applicationContext
    val controllerManager = remember { MediaControllerManager.getInstance(appContext) }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> controllerManager.initialize()
                Lifecycle.Event.ON_STOP -> controllerManager.release()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    return controllerManager.controller
}

fun MediaController.setPlaylistWithIndex(playlist: List<Song>, index: Int) {
    this.clearMediaItems()
    for (i in index..<(playlist.size - 1).coerceIn(0, 500)) {
        this.addMediaItem(MediaItem.fromUri(playlist[i].uri))
    }
    this.prepare()
    this.play()
}

fun MediaController.playerAction(playerAction: PlayerAction) {
    when (playerAction) {
        is PlayerAction.PlayPause -> {
            if (this.isPlaying) {
                this.pause()
            } else {
                this.play()
            }
        }

        is PlayerAction.Seek -> {
            this.seekTo(playerAction.position)
        }

        is PlayerAction.Next -> {
            this.seekToNext()
        }

        is PlayerAction.Previous -> {
            this.seekToPrevious()
        }
    }
}
