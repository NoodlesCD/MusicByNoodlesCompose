package com.example.musicbynoodlescompose

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.recreate
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.compose.rememberNavController
import com.example.musicbynoodlescompose.data.viewmodels.MediaContentViewModel
import com.example.musicbynoodlescompose.data.viewmodels.PlaylistsViewModel
import com.example.musicbynoodlescompose.ui.misc.BottomNavigationItem
import com.example.musicbynoodlescompose.ui.misc.Destination
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.player.PlayerState
import com.example.musicbynoodlescompose.ui.currentlyPlaying.SliderViewModel
import com.example.musicbynoodlescompose.player.addToQueue
import com.example.musicbynoodlescompose.player.playerAction
import com.example.musicbynoodlescompose.player.rememberMediaController
import com.example.musicbynoodlescompose.player.setPlaylistWithIndex
import com.example.musicbynoodlescompose.player.state
import com.example.musicbynoodlescompose.ui.main.components.BottomNavigationBar
import com.example.musicbynoodlescompose.ui.main.components.CurrentlyPlayingBar
import com.example.musicbynoodlescompose.ui.main.components.NavHostActions
import com.example.musicbynoodlescompose.ui.main.components.NavigationHost
import com.example.musicbynoodlescompose.ui.main.components.SleepTimerDialog
import com.example.musicbynoodlescompose.ui.theme.MusicByNoodlesComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.INFINITE
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.minutes

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 3 // || firstVisibleItemScrollOffset > 3

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MusicByNoodlesComposeTheme {
                CheckPermissions(this)

                val sliderState = viewModel<SliderViewModel>()
                val mediaController by rememberMediaController()
                var playerState: PlayerState? by remember { mutableStateOf(mediaController?.state()) }

                DisposableEffect(key1 = mediaController) {
                    mediaController?.run { playerState = state() }
                    onDispose { playerState?.dispose() }
                }

                LaunchedEffect(Unit) {
                    while (true) {
                        delay(1000)
                        if (playerState?.playbackState == ExoPlayer.STATE_READY || playerState?.isPlaying == true) {
                            var currPos = mediaController?.currentPosition ?: 0
                            var currDur = mediaController?.duration ?: 0

                            if (currPos < 0) currPos = 0
                            if (currDur < 0) currDur = 0
                            sliderState.state = sliderState.state.copy(
                                progress = currPos,
                                sliderPosition = currPos,
                                duration = currDur,
                                linearProgress = currPos.toFloat() / currDur.toFloat()
                            )
                        }
                    }
                }

                var isSleepDialogOpen by remember { mutableStateOf(false) }
                var sleepTimerDuration by remember { mutableStateOf(INFINITE) }
                LaunchedEffect(key1 = sleepTimerDuration) {
                    if (sleepTimerDuration == INFINITE) return@LaunchedEffect

                    delay(1000 * 60)
                    if (sleepTimerDuration != INFINITE) {
                        if (sleepTimerDuration == ZERO) {
                            mediaController?.stop()
                        } else {
                            sleepTimerDuration -= 1.minutes
                        }
                    }
                }

                val mediaContentViewModel = hiltViewModel<MediaContentViewModel>()
                val playlistsViewModel = hiltViewModel<PlaylistsViewModel>()

                val navController = rememberNavController()
                val navigationItems = bottomNavigationItems()
                var selectedItemIndex by remember { mutableIntStateOf(3) }

                var isPlayingBarVisible by remember { mutableStateOf(false) }

                navController.addOnDestinationChangedListener { _, navDestination, _ ->
                    val isCurrentSongScreen =
                        navDestination.route == Destination.CurrentlyPlaying.route
                    val isMediaPlaying = mediaController?.isPlaying == true ||
                            (mediaController?.currentPosition ?: 0) > 1

                    isPlayingBarVisible = !isCurrentSongScreen && isMediaPlaying
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Scaffold(
                        modifier = Modifier.background(Color.Transparent),
                        containerColor = Color.Transparent,
                        bottomBar = {
                                BottomNavigationBar(
                                    navigate = navController::navigate,
                                    bottomNavigationItems = navigationItems,
                                    selectedItemIndex = selectedItemIndex,
                                    selectedItemIndexChange = { selectedItemIndex = it }
                                )
                        }
                    ) { scaffoldPadding ->
                        Column(modifier = Modifier
                            .padding(scaffoldPadding)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.onBackground,
                                        MaterialTheme.colorScheme.background,
                                        MaterialTheme.colorScheme.onBackground,
                                    )
                                )
                            )
                        ) {
                            NavigationHost(
                                modifier = Modifier.weight(1f),
                                navController = navController,
                                mediaContent = mediaContentViewModel.media.value,
                                playlists = playlistsViewModel.playlists.value,
                                playerState = playerState,
                                sliderState = sliderState.state,
                                actions = NavHostActions(
                                    setPositionSlider = sliderState::setSlider,
                                    onAddToPlaylist = playlistsViewModel::updatePlaylist,
                                    onRemoveFromPlaylist = playlistsViewModel::removeFromPlaylist,
                                    onDeletePlaylist = playlistsViewModel::deletePlaylist,
                                    onAddToQueue = { mediaController?.addToQueue(it) },
                                    onSleepTimerSel = { isSleepDialogOpen = true },
                                    playerAction = { mediaController?.playerAction(it) },
                                    setCurrentPlaylist = { chosenPlaylist, chosenPosition ->
                                        mediaController?.setPlaylistWithIndex(chosenPlaylist, chosenPosition)
                                    },
                                )
                            )

                            CurrentlyPlayingBar(
                                isVisible = isPlayingBarVisible,
                                playerState = playerState,
                                sliderState = sliderState.state,
                                navToCurrentlyPlayingScreen = { navController.navigate(Destination.CurrentlyPlaying.route) },
                                playPauseAction = {
                                    mediaController?.playerAction(PlayerAction.PlayPause)
                                }
                            )
                        }
                    }
                }

                SleepTimerDialog(
                    context = applicationContext,
                    isSleepDialogOpen = isSleepDialogOpen,
                    setIsSleepDialogOpen = { isSleepDialogOpen = it },
                    setSleepTimerDuration = { sleepTimerDuration = it },
                    sleepTimerVal = sleepTimerDuration
                )
            }
        }
    }
}



/**
 * An adjustable determinate linear progress indicator.
 * Accepts values greater than 1f.
 *
 * @param modifier The modifier to be applied to the indicator.
 * @param progress The progress of the indicator, between 0 and [totalProgress].
 * @param totalProgress The total progress the indicator can reach.
 * @param tipWidth The width the tip of the progress indicator where progress has reached.
 * @param tipColor The color of the tip.
 * @param progressColor The color of the progressed section of the indicator.
 * @param backgroundColor The color of the section between [progress] and [totalProgress].
 * @param cornerRadius The corner radius of each of the elements. To only apply corner radius
 * to the outside border use the clip modifier instead.
 */
@Composable
fun AdjLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    totalProgress: Float,
    tipWidth: Dp = 3.dp,
    tipColor: Color = MaterialTheme.colorScheme.onPrimary,
    progressColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    cornerRadius: Dp = 0.dp
) {
    Canvas(modifier = modifier) {
        val progression: Float = ((progress.coerceIn(0f, totalProgress)) / (totalProgress)) * size.width

        drawRoundRect( // Background
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            color = backgroundColor,
            size = size
        )
        drawRoundRect( // Tip
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            color = tipColor,
            size = Size(width = progression, height = size.height)
        )
        drawRoundRect( // Progress
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            color = progressColor,
            size = Size(width = maxOf(0f, progression - tipWidth.toPx()), height = size.height)
        )
    }
}

/**
 * An adjustable determinate linear progress indicator.
 * Accepts values greater than 1f.
 *
 * @param modifier The modifier to be applied to the indicator.
 * @param progress The progress of the indicator, between 0 and [totalProgress].
 * @param totalProgress The total progress the indicator can reach.
 * @param tipWidth The width the tip of the progress indicator where progress has reached.
 * @param tipColor The color of the tip.
 * @param progressColor The brush of the progressed section of the indicator.
 * @param backgroundColor The brush of the section between [progress] and [totalProgress].
 * @param cornerRadius The corner radius of each of the elements. To only apply corner radius
 * to the outside border use the clip modifier instead.
 */
@Composable
fun AdjLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    totalProgress: Float,
    tipWidth: Dp = 3.dp,
    tipColor: Color = MaterialTheme.colorScheme.onPrimary,
    progressBrush: Brush,
    backgroundBrush: Brush,
    cornerRadius: Dp = 0.dp
) {
    Canvas(modifier = modifier) {
        val progression: Float = ((progress.coerceIn(0f, totalProgress)) / (totalProgress)) * size.width

        drawRoundRect( // Background
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            brush = backgroundBrush,
            size = size
        )
        drawRoundRect( // Tip
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            color = tipColor,
            size = Size(width = progression, height = size.height)
        )
        drawRoundRect( // Progress
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            brush = progressBrush,
            size = Size(width = maxOf(0f, progression - tipWidth.toPx()), height = size.height)
        )
    }
}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun CheckPermissions(activity: Activity) {
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

internal fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = "Playlists",
            route = Destination.PlaylistsLibrary.route,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
        ),
        BottomNavigationItem(
            title = "Artists",
            route = Destination.ArtistsLibrary.route,
            selectedIcon = Icons.Filled.People,
            unselectedIcon = Icons.Outlined.People
        ),
        BottomNavigationItem(
            title = "Albums",
            route = Destination.AlbumsLibrary.route,
            selectedIcon = Icons.Filled.Album,
            unselectedIcon = Icons.Outlined.Album
        ),
        BottomNavigationItem(
            title = "Songs",
            route = Destination.SongsLibrary.route,
            selectedIcon = Icons.Filled.MusicNote,
            unselectedIcon = Icons.Outlined.MusicNote
        )
    )
}