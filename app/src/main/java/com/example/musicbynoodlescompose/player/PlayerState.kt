package com.example.musicbynoodlescompose.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.Player

fun Player.state(): PlayerState {
    return PlayerStateImpl(this)
}

interface PlayerState {

    val player: Player

    @get:Player.State
    var playbackState: Int
    var isPlaying: Boolean
    val currentMediaItem: MediaItem?

    fun dispose()
}

internal class PlayerStateImpl(
    override val player: Player,
) : PlayerState {

    @get:Player.State
    override var playbackState: Int by mutableStateOf(player.playbackState)
    override var isPlaying: Boolean by mutableStateOf(player.isPlaying)
    override var currentMediaItem: MediaItem? by mutableStateOf(player.currentMediaItem)

    private val listener = object : Player.Listener {
        override fun onPlaybackStateChanged(@Player.State playbackState: Int) {
            this@PlayerStateImpl.playbackState = playbackState
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            this@PlayerStateImpl.isPlaying = isPlaying
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            this@PlayerStateImpl.currentMediaItem = mediaItem
        }
    }

    init {
        player.addListener(listener)
    }

    override fun dispose() {
        player.removeListener(listener)
    }
}