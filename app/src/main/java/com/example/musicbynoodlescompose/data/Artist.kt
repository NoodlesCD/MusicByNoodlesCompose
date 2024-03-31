package com.example.musicbynoodlescompose.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    var name: String = "",
    var albums: MutableList<Album> = mutableListOf(),
    var songCount: Int = 0
): Parcelable