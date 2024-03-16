package com.example.musicbynoodlescompose.player

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.currentlyPlaying.CurrentlyPlayingState

class ExoPlayerViewModel: ViewModel() {

    var currentlyPlayingState by mutableStateOf(CurrentlyPlayingState())

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSource: MediaItem
    private lateinit var playlist: List<Song>
    private var playlistPosition: Int = 0

    fun initialize(context: Context) {
        exoPlayer = ExoPlayer.Builder(context).build()
    }

    fun release() {
        exoPlayer.release()
    }

    fun setPlaylist(playlist: List<Song>, index: Int) {
        this.playlist = playlist
        this.playlistPosition = index
        playMediaSource(this.playlist[playlistPosition].uri)
//        exoPlayer.clearMediaItems()
//
//        for (i in index..< playlist.size) {
//            exoPlayer.addMediaItem(MediaItem.fromUri(playlist[i].uri))
//        }
//        for (i in 0..< index) {
//            exoPlayer.addMediaItem(MediaItem.fromUri(playlist[i].uri))
//        }

        exoPlayer.play()
    }

    fun playMediaSource(uri: Uri) {
        mediaSource = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
        exoPlayer.play()

        currentlyPlayingState = currentlyPlayingState.copy(
            song = playlist[playlistPosition],
            isPlaying = true
        )
    }

    fun isPlaying() = exoPlayer.isPlaying

    fun currentPosition() = exoPlayer.currentPosition

    fun playerAction(playerAction: PlayerAction) {
        when (playerAction) {
            is PlayerAction.PlayPause -> {
                currentlyPlayingState = if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                    currentlyPlayingState.copy(
                        isPlaying = false
                    )
                } else {
                    exoPlayer.play()
                    currentlyPlayingState.copy(
                        isPlaying = true
                    )
                }
            }

            is PlayerAction.Seek -> {
                exoPlayer.seekTo(playerAction.position)
                currentlyPlayingState = currentlyPlayingState.copy(
                    progress = playerAction.position,
                    sliderPosition = playerAction.position
                )
            }

            is PlayerAction.Next -> {
                playlistPosition++
                if (playlistPosition >= playlist.size) {
                    playlistPosition = 0
                }

                playMediaSource(playlist[playlistPosition].uri)
            }

            is PlayerAction.Previous -> {
                playlistPosition--
                if (playlistPosition < 0) {
                    playlistPosition = playlist.size - 1
                }

                playMediaSource(playlist[playlistPosition].uri)
            }
        }
    }

    fun resetSlider() {
        currentlyPlayingState = currentlyPlayingState.copy(
            sliderPosition = 0
        )
    }
}