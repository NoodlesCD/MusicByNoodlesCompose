package com.example.musicbynoodlescompose.data.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicbynoodlescompose.data.PlaylistManager
import com.example.musicbynoodlescompose.data.models.Playlist
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(private val playlistManager: PlaylistManager): ViewModel() {

    val playlists = mutableStateOf(playlistManager.getUsersPlaylists())

    fun updatePlaylist(playlist: Playlist) = playlistManager.updatePlaylist(playlist)
        .let { playlists.value = it }

    fun deletePlaylist(playlist: Playlist) = playlistManager.deletePlaylist(playlist)
        .let { playlists.value = it }

    fun removeFromPlaylist(playlist: Playlist, index: Int) = playlistManager
        .removeFromPlaylist(playlist, index)
        .let { playlists.value = it }
}