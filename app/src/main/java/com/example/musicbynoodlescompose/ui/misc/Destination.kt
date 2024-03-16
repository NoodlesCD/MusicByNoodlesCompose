package com.example.musicbynoodlescompose.ui.misc

sealed class Destination(val route: String) {
    data object ArtistsLibrary: Destination("artists")
    data object AlbumsLibrary: Destination("albums")
    data object SongsLibrary: Destination("songs")
    data object CurrentArtist: Destination("current_artist")
    data object CurrentAlbum: Destination("current_album")
    data object CurrentlyPlaying: Destination("currently_playing")
}