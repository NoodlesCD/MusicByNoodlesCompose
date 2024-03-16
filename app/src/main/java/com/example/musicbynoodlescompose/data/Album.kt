package com.example.musicbynoodlescompose.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val title: String,
    val artist: String,
    val artistId: Long,
    val songs: MutableList<Song>,
    val albumUri: Uri,
): Parcelable