package com.example.musicbynoodlescompose.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Playlist (
    val title: String = "",
    val songs: List<Song> = arrayListOf()
)