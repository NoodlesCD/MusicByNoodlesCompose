package com.example.musicbynoodlescompose.data.models

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.example.musicbynoodlescompose.ui.library.LibraryItem
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Artist(
    var name: String = "",
    var albums: MutableList<Album> = mutableListOf(),
    var songCount: Int = 0
): Parcelable, LibraryItem {
    override val rowImage: String
        get() = albums[0].albumUri
    override val primaryText: String
        get() = name
    override val secondaryText: String
        get() = if (songCount > 1) "${songCount} songs" else "1 song"
    override val searchParameter: String
        get() = name
}