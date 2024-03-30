package com.example.musicbynoodlescompose.player

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.currentlyPlaying.CurrentlyPlayingState

class PlayerViewModel: ViewModel() {

    var currentlyPlayingState by mutableStateOf(CurrentlyPlayingState())

    private lateinit var currentSong: Song
    private lateinit var playlist: List<Song>
    private var playlistPosition: Int = 0

    fun setPlaylistAndIndex(mediaController: MediaController?, playlist: List<Song>, index: Int) {
        mediaController?.clearMediaItems()

        for (i in index..<(playlist.size - 1).coerceIn(0, 500)) {
            mediaController?.addMediaItem(MediaItem.fromUri(playlist[i].uri))
        }
        currentlyPlayingState = currentlyPlayingState.copy(
            song = playlist[index]
        )

        mediaController?.prepare()
        mediaController?.play()
//        this.playlist = playlist
//        this.playlistPosition = index
//        this.currentSong = playlist[playlistPosition]
    }

    fun currentSong(): Song {
        return currentSong
    }

    fun prevSongUri(): Uri {
        playlistPosition--
        if (playlistPosition < 0) {
            playlistPosition = playlist.size - 1
        }
        currentSong = playlist[playlistPosition]
        return currentSong.uri
    }

    fun nextSongUri(): Uri {
        playlistPosition++
        if (playlistPosition >= playlist.size) {
            playlistPosition = 0
        }
        currentSong = playlist[playlistPosition]
        return currentSong.uri
    }

//    fun play(mediaController: MediaController?) {
//        mediaController?.setMediaItem(MediaItem.fromUri(playlist[playlistPosition].uri))
//        mediaController?.prepare()
//        mediaController?.play()
//        currentlyPlayingState = currentlyPlayingState.copy(
//            isPlaying = true
//        )
//    }

    fun playerAction(mediaController: MediaController?, playerAction: PlayerAction) {
        when (playerAction) {
            is PlayerAction.PlayPause -> {
                currentlyPlayingState = if (mediaController?.isPlaying == true) {
                    mediaController.pause()
                    currentlyPlayingState.copy(
                        isPlaying = false
                    )
                } else {
                    mediaController?.play()
                    currentlyPlayingState.copy(
                        isPlaying = true
                    )
                }
            }

            is PlayerAction.Seek -> {
                mediaController?.seekTo(playerAction.position)
                currentlyPlayingState = currentlyPlayingState.copy(
                    progress = playerAction.position,
                    sliderPosition = playerAction.position
                )
            }

            is PlayerAction.Next -> {
                mediaController?.seekToNext()
//                playlistPosition++
//                if (playlistPosition >= playlist.size) {
//                    playlistPosition = 0
//                }
//                mediaController?.setMediaItem(MediaItem.fromUri(playlist[playlistPosition].uri))
//                mediaController?.prepare()
//                mediaController?.play()
                currentlyPlayingState.copy(
                    isPlaying = true
                )
            }

            is PlayerAction.Previous -> {
                mediaController?.seekToPrevious()
//                playlistPosition--
//                if (playlistPosition < 0) {
//                    playlistPosition = playlist.size - 1
//                }
//                mediaController?.setMediaItem(MediaItem.fromUri(playlist[playlistPosition].uri))
//                mediaController?.prepare()
//                mediaController?.play()
                currentlyPlayingState.copy(
                    isPlaying = true
                )
            }
        }
    }

    fun resetSlider() {
        currentlyPlayingState = currentlyPlayingState.copy(
            sliderPosition = 0
        )
    }
}