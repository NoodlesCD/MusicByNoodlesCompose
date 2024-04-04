package com.example.musicbynoodlescompose.ui.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicbynoodlescompose.data.models.MediaContent
import com.example.musicbynoodlescompose.data.models.Playlist
import com.example.musicbynoodlescompose.data.models.Song
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.player.PlayerState
import com.example.musicbynoodlescompose.data.viewmodels.LibraryListStates
import com.example.musicbynoodlescompose.ui.albums.AlbumsLibraryActions
import com.example.musicbynoodlescompose.ui.albums.AlbumsLibraryScreen
import com.example.musicbynoodlescompose.ui.albums.CurrentAlbumScreen
import com.example.musicbynoodlescompose.data.viewmodels.NavigationViewModel
import com.example.musicbynoodlescompose.ui.artists.ArtistsLibraryScreen
import com.example.musicbynoodlescompose.ui.artists.CurrentArtistScreen
import com.example.musicbynoodlescompose.ui.currentlyPlaying.CurrentlyPlayingScreen
import com.example.musicbynoodlescompose.ui.currentlyPlaying.SliderState
import com.example.musicbynoodlescompose.ui.misc.Destination
import com.example.musicbynoodlescompose.ui.misc.TransitionAnimation
import com.example.musicbynoodlescompose.ui.playlists.AddToPlaylistScreen
import com.example.musicbynoodlescompose.ui.playlists.CurrentPlaylist
import com.example.musicbynoodlescompose.ui.playlists.PlaylistsLibrary
import com.example.musicbynoodlescompose.ui.songs.SongLibraryActions
import com.example.musicbynoodlescompose.ui.songs.SongsLibrary

data class NavHostActions(
    val setPositionSlider: (position: Long) -> Unit,
    val setCurrentPlaylist: (List<Song>, Int) -> Unit,
    val onAddToPlaylist: (playlist: Playlist) -> Unit,
    val onRemoveFromPlaylist: (playlist: Playlist, index: Int) -> Unit,
    val onDeletePlaylist: (playlist: Playlist) -> Unit,
    val onAddToQueue: (songs: List<Song>) -> Unit,
    val onSleepTimerSel: () -> Unit,
    val playerAction: (PlayerAction) -> Unit,
)

@Composable
fun NavigationHost(
    actions: NavHostActions,
    navController: NavHostController,
    mediaContent: MediaContent,
    playlists: List<Playlist>,
    playerState: PlayerState?,
    sliderState: SliderState,
    modifier: Modifier = Modifier
) {
    val navigationViewModel = viewModel<NavigationViewModel>()
    val listStates = viewModel<LibraryListStates>()

    Row(
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Destination.SongsLibrary.route
        ) {
            composable(
                route = Destination.PlaylistsLibrary.route,
                enterTransition = TransitionAnimation.PlaylistsLibrary.enterTransition,
                exitTransition = TransitionAnimation.PlaylistsLibrary.exitTransition
            ) {
                PlaylistsLibrary(
                    playlistsLibrary = playlists,
                    onPlaylistSelected = {
                        navigationViewModel.currentPlaylist = it
                        navController.navigate(Destination.CurrentPlaylist.route)
                    },
                    onDeletePlaylist = actions.onDeletePlaylist,
                    listState = listStates.playlistsState.value,
                    onSleepTimerSelected = actions.onSleepTimerSel
                )
            }
            composable(
                route = Destination.CurrentPlaylist.route
            ) {
                CurrentPlaylist(
                    playlist = navigationViewModel.currentPlaylist,
                    onSongSelected = { playlist, index ->
                        actions.setCurrentPlaylist(playlist, index)
                        navController.navigate(Destination.CurrentlyPlaying.route)
                    },
                    onArtistSelected = { selectedArtistId ->
                        navigationViewModel.currentArtist = mediaContent.artistsMap[selectedArtistId]!!
                        navController.navigate(Destination.CurrentArtist.route)
                    },
                    onAlbumSelected = { selectedAlbumId ->
                        navigationViewModel.currentAlbum = mediaContent.albumsMap[selectedAlbumId]!!
                        navController.navigate(Destination.CurrentAlbum.route)
                    },
                    addToQueue = { song -> actions.onAddToQueue(listOf(song)) },
                    removeFromPlaylist = actions.onRemoveFromPlaylist
                )
            }
            composable(
                route = Destination.AddToPlaylist.route
            ) {
                AddToPlaylistScreen(
                    songsToAdd = navigationViewModel.songToAdd,
                    playlistsLibrary = playlists,
                    onAddToPlaylistSelected = { playlist ->
                        actions.onAddToPlaylist(playlist)
                        navController.navigate(Destination.PlaylistsLibrary.route) {
                            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(
                route = Destination.ArtistsLibrary.route,
                enterTransition = TransitionAnimation.ArtistsLibrary.enterTransition,
                exitTransition = TransitionAnimation.ArtistsLibrary.exitTransition
            ) {
                ArtistsLibraryScreen(
                    libraryItems = mediaContent.artistsAlphabetical,
                    onArtistSelected = { selectedArtist ->
                        navigationViewModel.currentArtist = selectedArtist
                        navController.navigate(Destination.CurrentArtist.route)
                    },
                    listState = listStates.artistsState.value,
                    onSleepTimerSelected = actions.onSleepTimerSel
                )
            }
            composable(
                route = Destination.AlbumsLibrary.route,
                enterTransition = TransitionAnimation.AlbumsLibrary.enterTransition,
                exitTransition = TransitionAnimation.AlbumsLibrary.exitTransition
            ) {
                AlbumsLibraryScreen(
                    libraryItems = mediaContent.albumsAlphabetical,
                    listState = listStates.albumsState.value,
                    actions = AlbumsLibraryActions(
                        onViewAlbumSelected = { selectedAlbum ->
                            navigationViewModel.currentAlbum = selectedAlbum
                            navController.navigate(Destination.CurrentAlbum.route)
                        },
                        onViewArtistSelected = { selectedArtistId ->
                            navigationViewModel.currentArtist = mediaContent.artistsMap[selectedArtistId]!!
                            navController.navigate(Destination.CurrentArtist.route)
                        },
                        onAddToPlaylistSelected = { songsToAdd ->
                            navigationViewModel.songToAdd = songsToAdd
                            navController.navigate(Destination.AddToPlaylist.route)
                        },
                        onAddToQueueSelected = actions.onAddToQueue,
                        onSleepTimerSelected = actions.onSleepTimerSel,
                    )
                )
            }
            composable(
                route = Destination.SongsLibrary.route,
                enterTransition = TransitionAnimation.SongsLibrary.enterTransition,
                exitTransition = TransitionAnimation.SongsLibrary.exitTransition
            ) {
                SongsLibrary(
                    libraryItems = mediaContent.songsAlphabetical,
                    listState = listStates.songsState.value,
                    actions = SongLibraryActions(
                        onPlaySongSelected = { selectedSong ->
                            actions.setCurrentPlaylist(mediaContent.songsAlphabetical, selectedSong)
                            navController.navigate(Destination.CurrentlyPlaying.route)
                        },
                        onViewAlbumSelected = { selectedAlbumId ->
                            navigationViewModel.currentAlbum = mediaContent.albumsMap[selectedAlbumId]!!
                            navController.navigate(Destination.CurrentAlbum.route)
                        },
                        onViewArtistSelected = { selectedArtistId ->
                            navigationViewModel.currentArtist = mediaContent.artistsMap[selectedArtistId]!!
                            navController.navigate(Destination.CurrentArtist.route)
                        },
                        onAddToPlaylistSelected = { selectedSong ->
                            navigationViewModel.songToAdd = listOf(selectedSong)
                            navController.navigate(Destination.AddToPlaylist.route)
                        },
                        onAddToQueueSelected = { song -> actions.onAddToQueue(listOf(song)) },
                        onSleepTimerSelected = actions.onSleepTimerSel,
                    )
                )
            }
            composable(
                route = Destination.CurrentArtist.route,
                enterTransition = TransitionAnimation.CurrentArtist.enterTransition,
                exitTransition = TransitionAnimation.CurrentArtist.exitTransition
            ) {
                CurrentArtistScreen(
                    artist = navigationViewModel.currentArtist,
                    onAlbumSelected = { selectedAlbum ->
                        navigationViewModel.currentAlbum = selectedAlbum
                        navController.navigate(Destination.CurrentAlbum.route)
                    },
                    addToQueue = actions.onAddToQueue,
                    addToPlaylist = { selectedSongs ->
                        navigationViewModel.songToAdd = selectedSongs
                        navController.navigate(Destination.AddToPlaylist.route)
                    }
                )
            }
            composable(
                route = Destination.CurrentAlbum.route,
                enterTransition = TransitionAnimation.CurrentAlbum.enterTransition,
                exitTransition = TransitionAnimation.CurrentAlbum.exitTransition
            ) {
                CurrentAlbumScreen(
                    album = navigationViewModel.currentAlbum,
                    onSongSelected = { selectedSong ->
                        actions.setCurrentPlaylist(navigationViewModel.currentAlbum.songs, selectedSong)
                        navController.navigate(Destination.CurrentlyPlaying.route)
                    },
                    onArtistSelected = { selectedArtistId ->
                        if (mediaContent.artistsMap[selectedArtistId] != null) {
                            navigationViewModel.currentArtist = mediaContent.artistsMap[selectedArtistId]!!
                            navController.navigate(Destination.CurrentArtist.route)
                        }
                    },
                    addSongToPlaylist = { selectedSong ->
                        navigationViewModel.songToAdd = listOf(selectedSong)
                        navController.navigate(Destination.AddToPlaylist.route)
                    },
                    addToQueue = { song -> actions.onAddToQueue(listOf(song)) }
                )
            }
            composable(
                route = Destination.CurrentlyPlaying.route,
                enterTransition = TransitionAnimation.CurrentlyPlaying.enterTransition,
                exitTransition = TransitionAnimation.CurrentlyPlaying.exitTransition
            ) {
                CurrentlyPlayingScreen(
                    state = playerState,
                    sliderState = sliderState,
                    onAction = {
                        actions.playerAction(it)
                    },
                    setSlider = actions.setPositionSlider
                )
            }
        }
    }
}