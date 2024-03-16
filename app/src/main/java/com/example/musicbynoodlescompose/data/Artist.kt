package com.example.musicbynoodlescompose.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    var name: String,
    var albums: MutableList<Album>,
    var songCount: Int
): Parcelable