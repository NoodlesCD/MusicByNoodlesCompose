package com.example.musicbynoodlescompose.data.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicbynoodlescompose.data.MediaResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaContentViewModel @Inject constructor(mediaResolver: MediaResolver): ViewModel() {

    val media = mutableStateOf(mediaResolver.getMediaContent())
}