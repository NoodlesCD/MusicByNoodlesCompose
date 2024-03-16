package com.example.musicbynoodlescompose.player

sealed class PlayerAction {
    data object PlayPause: PlayerAction()
    data object Next: PlayerAction()
    data object Previous: PlayerAction()
    data class Seek(val position: Long): PlayerAction()
}