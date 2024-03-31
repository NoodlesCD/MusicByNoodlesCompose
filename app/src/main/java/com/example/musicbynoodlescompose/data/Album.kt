package com.example.musicbynoodlescompose.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val title: String = "",
    val artist: String = "",
    val artistId: Long = 0L,
    val songs: MutableList<Song> = mutableListOf(),
    val albumUri: Uri = Uri.EMPTY,
): Parcelable