package com.example.musicbynoodlescompose.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.media3.common.MediaItem

data class MediaContent(
    var artistsMap: MutableMap<Long, Artist>,
    var albumsMap: MutableMap<Long, Album>,
    var artistsAlphabetical: List<Artist>,
    var albumsAlphabetical: List<Album>,
    var songsAlphabetical: List<Song>
)