package com.example.musicbynoodlescompose.player

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.example.musicbynoodlescompose.data.models.Song

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
    this.stop()
    this.clearMediaItems()

    val playlistType = Bundle()
    playlistType.putString("playlistType", "main_playlist")

    for (i in index..<playlist.size) {
        val currentSong = playlist[i]
        val mediaMetadata = MediaMetadata.Builder()
            .setArtworkUri(currentSong.imageUri.toUri())
            .setArtist(currentSong.artist)
            .setAlbumTitle(currentSong.album)
            .setTitle(currentSong.title)
            .setExtras(playlistType)
            .build()

        val mediaItem = MediaItem.Builder()
            .setMediaMetadata(mediaMetadata)
            .setUri(currentSong.uri)
            .setTag("main_queue")
            .build()

        this.addMediaItem(mediaItem)
        if (i >= index + 100) break
    }
    this.prepare()
    this.play()
}

fun MediaController.addToQueue(queue: List<Song>) {
    var queuePosition = 1

    while (this.getMediaItemAt(queuePosition)
            .mediaMetadata
            .extras
            ?.getString("playlistType") != "main_playlist") {
        queuePosition++
    }

    val playlistType = Bundle()
    playlistType.putString("playlistType", "queue")

    for (song in queue) {
        val mediaMetadata = MediaMetadata.Builder()
            .setArtworkUri(song.imageUri.toUri())
            .setArtist(song.artist)
            .setAlbumTitle(song.album)
            .setTitle(song.title)
            .setExtras(playlistType)
            .build()

        val mediaItem = MediaItem.Builder()
            .setMediaMetadata(mediaMetadata)
            .setUri(song.uri)
            .build()

        this.addMediaItem(queuePosition, mediaItem)
        queuePosition++
    }
}

fun MediaController.playerAction(playerAction: PlayerAction) = when (playerAction) {
    is PlayerAction.Next -> this.seekToNext()
    is PlayerAction.Previous -> this.seekToPrevious()
    is PlayerAction.Seek -> this.seekTo(playerAction.position)
    is PlayerAction.PlayPause -> {
        if (this.isPlaying) {
            this.pause()
        } else {
            this.play()
        }
    }
}
