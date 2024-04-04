package com.example.musicbynoodlescompose.data.viewmodels

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LibraryListStates: ViewModel() {
    var playlistsState = mutableStateOf(LazyListState())
    var artistsState  = mutableStateOf(LazyListState())
    var albumsState = mutableStateOf(LazyListState())
    var songsState = mutableStateOf(LazyListState())
}