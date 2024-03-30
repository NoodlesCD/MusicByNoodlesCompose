package com.example.musicbynoodlescompose.data

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import com.example.musicbynoodlescompose.MediaResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    mediaResolver: MediaResolver
): ViewModel() {

    val media = mutableStateOf(mediaResolver.getMediaContent())
}