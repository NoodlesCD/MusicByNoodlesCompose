package com.example.musicbynoodlescompose.data

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Song (
    val id: Long = 0,
    val title: String = "",
    val album: String = "",
    val artist: String = "",
    val cdTrackNumber: Int = 0,
    val albumId: Long = 0,
    val artistId: Long = 0,
    val duration: Int = 0,
    val uri: String = "",
    val imageUri: String = "",
) : Parcelable