package com.example.musicbynoodlescompose.data.models

import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Artist
import com.example.musicbynoodlescompose.data.models.Song

data class MediaContent(
    var artistsMap: MutableMap<Long, Artist>,
    var albumsMap: MutableMap<Long, Album>,
    var artistsAlphabetical: List<Artist>,
    var albumsAlphabetical: List<Album>,
    var songsAlphabetical: List<Song>
)