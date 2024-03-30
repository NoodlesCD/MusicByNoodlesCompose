package com.example.musicbynoodlescompose.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class PlaylistFileManager @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    private val usersPlaylists: ArrayList<Playlist> = arrayListOf()

    init {
        val files: Array<String> = appContext.fileList()
        for (file in files) {
            usersPlaylists.add(Json.decodeFromString(file))
        }
    }

    fun updatePlaylist(playlist: Playlist) {
        appContext.openFileOutput(playlist.title, Context.MODE_PRIVATE).use {
            Json.encodeToString(playlist)
        }
    }
}