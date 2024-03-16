package com.example.musicbynoodlescompose

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.MoreExecutors
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Artist
import com.example.musicbynoodlescompose.data.ContentViewModel
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.player.ExoPlayerViewModel
import com.example.musicbynoodlescompose.player.PlaybackService
import com.example.musicbynoodlescompose.ui.misc.BottomNavigationItem
import com.example.musicbynoodlescompose.ui.misc.Destination
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.ui.albums.AlbumsLibrary
import com.example.musicbynoodlescompose.ui.albums.CurrentAlbum
import com.example.musicbynoodlescompose.ui.albums.CurrentAlbumViewModel
import com.example.musicbynoodlescompose.ui.artists.ArtistsLibrary
import com.example.musicbynoodlescompose.ui.artists.CurrentArtist
import com.example.musicbynoodlescompose.ui.artists.CurrentArtistViewModel
import com.example.musicbynoodlescompose.ui.currentlyPlaying.CurrentlyPlayingScreen
import com.example.musicbynoodlescompose.ui.currentlyPlaying.CurrentlyPlayingState
import com.example.musicbynoodlescompose.ui.songs.SongsLibrary
import com.example.musicbynoodlescompose.ui.theme.MusicByNoodlesComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicByNoodlesComposeTheme {
                checkPermissions(activity = this)


//                val sessionToken =
//                    SessionToken(this, ComponentName(this, PlaybackService::class.java))
//                val controllerFuture =
//                    MediaController.Builder(this, sessionToken).buildAsync()
//                controllerFuture.addListener({
//                    // MediaController is available here with controllerFuture.get()
//                }, MoreExecutors.directExecutor())

                val contentViewModel = viewModel<ContentViewModel>()
                contentViewModel.initialize(context = this)
                val exoPlayer = viewModel<ExoPlayerViewModel>()
                exoPlayer.initialize(this)

                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navigate = navController::navigate) }
                    ) { scaffoldPadding ->
                        NavigationHost(
                            scaffoldPadding = scaffoldPadding,
                            navController = navController,
                            artistsLibrary = contentViewModel.artistsAlphabetical,
                            albumsLibrary = contentViewModel.albumsAlphabetical,
                            songsLibrary = contentViewModel.songsAlphabetical,
                            currentlyPlayingState = exoPlayer.currentlyPlayingState,
                            setPlaylist = exoPlayer::setPlaylist,
                            playerAction = exoPlayer::playerAction,
                            resetSlider = exoPlayer::resetSlider
                        )
                    }
                }
                
                LaunchedEffect(Unit) {
                    while (true) {
                        delay(1000)
                        exoPlayer.currentlyPlayingState = exoPlayer.currentlyPlayingState.copy(
                            progress = exoPlayer.currentPosition(),
                            sliderPosition = exoPlayer.currentPosition()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkPermissions(activity: Activity) {
    val readMediaPermission: PermissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(permission = Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    LaunchedEffect(readMediaPermission.status.isGranted) {
        if (readMediaPermission.status.isGranted) {
            Log.i("MainActivity.kt", "Permissions granted")
        } else {
            readMediaPermission.launchPermissionRequest()
            recreate(activity)
        }
    }
}

@Composable
fun NavigationHost(
    scaffoldPadding: PaddingValues,
    navController: NavHostController,
    artistsLibrary: List<Artist>,
    albumsLibrary: List<Album>,
    songsLibrary: List<Song>,
    currentlyPlayingState: CurrentlyPlayingState,
    setPlaylist: (List<Song>, Int) -> Unit,
    playerAction: (PlayerAction) -> Unit,
    resetSlider: () -> Unit
) {
    val currentArtistViewModel = viewModel<CurrentArtistViewModel>()
    val currentAlbumViewModel = viewModel<CurrentAlbumViewModel>()

    Column(
        modifier = Modifier
            .padding(scaffoldPadding)
    ) {
        NavHost(
            navController = navController,
            startDestination = Destination.SongsLibrary.route
        ) {
            composable(route = Destination.ArtistsLibrary.route) {
                ArtistsLibrary(
                    artistsLibrary = artistsLibrary,
                    onArtistSelected = { selectedArtist ->
                        currentArtistViewModel.currentArtist = selectedArtist
                        navController.navigate(Destination.CurrentArtist.route)
                    }
                )
            }
            composable(route = Destination.AlbumsLibrary.route) {
                AlbumsLibrary(
                    albumsLibrary = albumsLibrary,
                    onAlbumSelected = { selectedAlbum ->
                        currentAlbumViewModel.currentAlbum = selectedAlbum
                        navController.navigate(Destination.CurrentAlbum.route)
                    }
                )
            }
            composable(route = Destination.SongsLibrary.route) {
                SongsLibrary(
                    songsLibrary = songsLibrary,
                    onSongSelected = { selectedSong ->
                        setPlaylist(songsLibrary, selectedSong)
                        navController.navigate(Destination.CurrentlyPlaying.route)
                    }
                )
            }
            composable(route = Destination.CurrentArtist.route) {
                CurrentArtist(
                    artist = currentArtistViewModel.currentArtist,
                    onAlbumSelected = { selectedAlbum ->
                        currentAlbumViewModel.currentAlbum = selectedAlbum
                        navController.navigate(Destination.CurrentAlbum.route)
                    }
                )
            }
            composable(route = Destination.CurrentAlbum.route) {
                CurrentAlbum(
                    album = currentAlbumViewModel.currentAlbum,
                    onSongSelected = { selectedSong ->
                        setPlaylist(currentAlbumViewModel.currentAlbum.songs, selectedSong)
                        navController.navigate(Destination.CurrentlyPlaying.route)
                    }
                )
            }
            composable(route = Destination.CurrentlyPlaying.route) {
                CurrentlyPlayingScreen(
                    state = currentlyPlayingState,
                    resetSlider = resetSlider
                ) { playerAction(it) }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navigate: (String) -> Unit
) {
    var selectedItemIndex by remember { mutableIntStateOf(2) }
    val bottomNavigationItems = bottomNavigationItems()

    NavigationBar(
        modifier = Modifier.background(Color.Transparent),
        containerColor = Color.Transparent
    ) {
        bottomNavigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.background(Color.Transparent),
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector =
                            if (selectedItemIndex == index) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}

fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = "Artists",
            route = Destination.ArtistsLibrary.route,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        ),
        BottomNavigationItem(
            title = "Albums",
            route = Destination.AlbumsLibrary.route,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List
        ),
        BottomNavigationItem(
            title = "Songs",
            route = Destination.SongsLibrary.route,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List
        )
    )
}