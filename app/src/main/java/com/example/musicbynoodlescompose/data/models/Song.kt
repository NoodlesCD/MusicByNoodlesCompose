package com.example.musicbynoodlescompose.data.models

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.example.musicbynoodlescompose.ui.library.LibraryItem
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Stable
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
) : Parcelable, LibraryItem {
    override val rowImage: String
        get() = imageUri
    override val primaryText: String
        get() = title
    override val secondaryText: String
        get() = artist
    override val searchParameter: String
        get() = title
}