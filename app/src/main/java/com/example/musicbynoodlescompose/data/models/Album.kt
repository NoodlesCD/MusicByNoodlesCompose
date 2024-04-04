package com.example.musicbynoodlescompose.data.models

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.example.musicbynoodlescompose.ui.library.LibraryItem
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Album(
    val title: String = "",
    val artist: String = "",
    val artistId: Long = 0L,
    val songs: MutableList<Song> = mutableListOf(),
    val albumUri: String = "",
): Parcelable, LibraryItem {
    override val rowImage: String
        get() = albumUri
    override val primaryText: String
        get() = title
    override val secondaryText: String
        get() = artist
    override val searchParameter: String
        get() = title
}