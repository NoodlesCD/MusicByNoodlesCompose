package com.example.musicbynoodlescompose.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import javax.inject.Inject

class PlaylistManager @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    private var usersPlaylists: ArrayList<Playlist> = arrayListOf()

    init {
        val files: Array<out File>? = appContext.getDir("playlists", Context.MODE_PRIVATE).listFiles()
        if (files != null) {
            for (file in files) {
                val input = file.inputStream().readBytes().toString(Charset.defaultCharset())
                usersPlaylists.add(Json.decodeFromString(input))
            }
        }
    }

    fun updatePlaylist(playlistToUpdate: Playlist): List<Playlist> {
        usersPlaylists = usersPlaylists.map { playlist ->
            if (playlist.title == playlistToUpdate.title) playlistToUpdate else playlist
        } as ArrayList<Playlist>

        if (usersPlaylists.find { playlist -> playlist.title == playlistToUpdate.title } == null) {
            usersPlaylists += playlistToUpdate
        }

        writeToFile(playlistToUpdate)

        return getUsersPlaylists()
    }

    fun removeFromPlaylist(playlistToUpdate: Playlist, index: Int): List<Playlist> {
        val newSongsList = playlistToUpdate.songs.toMutableList()
        newSongsList.removeAt(index)

        val updatedPlaylist = Playlist(playlistToUpdate.title, newSongsList)

        usersPlaylists = usersPlaylists.map { playlist ->
            if (playlist.title == playlistToUpdate.title) updatedPlaylist else playlist
        } as ArrayList<Playlist>

        writeToFile(updatedPlaylist)

        return getUsersPlaylists()
    }

    fun deletePlaylist(playlistToDelete: Playlist): List<Playlist> {
        for (i in usersPlaylists.indices) {
            if (usersPlaylists[i].title == playlistToDelete.title) {
                usersPlaylists.removeAt(i)
            }
        }

        val directory = appContext.getDir("playlists", Context.MODE_PRIVATE)
        val file = File(directory, playlistToDelete.title)
        file.delete()

        return getUsersPlaylists()
    }

    private fun writeToFile(playlist: Playlist) {
        val directory = appContext.getDir("playlists", Context.MODE_PRIVATE)
        val file = File(directory, playlist.title)

        FileOutputStream(file).use { output ->
            output.write(Json.encodeToString(playlist).toByteArray())
        }
    }

    fun getUsersPlaylists(): List<Playlist> {
        return usersPlaylists.toList()
    }
}