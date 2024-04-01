package com.example.musicbynoodlescompose

import TransitionAnimation
import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.transition.TransitionSet
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.recreate
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.data.MediaContent
import com.example.musicbynoodlescompose.data.MediaContentViewModel
import com.example.musicbynoodlescompose.data.Playlist
import com.example.musicbynoodlescompose.data.PlaylistsViewModel
import com.example.musicbynoodlescompose.data.Song
import com.example.musicbynoodlescompose.ui.misc.BottomNavigationItem
import com.example.musicbynoodlescompose.ui.misc.Destination
import com.example.musicbynoodlescompose.player.PlayerAction
import com.example.musicbynoodlescompose.player.PlayerState
import com.example.musicbynoodlescompose.player.SliderViewModel
import com.example.musicbynoodlescompose.player.addToQueue
import com.example.musicbynoodlescompose.player.playerAction
import com.example.musicbynoodlescompose.player.rememberMediaController
import com.example.musicbynoodlescompose.player.setPlaylistWithIndex
import com.example.musicbynoodlescompose.player.state
import com.example.musicbynoodlescompose.ui.LibraryListStates
import com.example.musicbynoodlescompose.ui.SongProgressIndicator
import com.example.musicbynoodlescompose.ui.albums.AlbumsLibrary
import com.example.musicbynoodlescompose.ui.albums.CurrentAlbum
import com.example.musicbynoodlescompose.ui.albums.NavigationViewModel
import com.example.musicbynoodlescompose.ui.artists.ArtistsLibrary
import com.example.musicbynoodlescompose.ui.artists.CurrentArtist
import com.example.musicbynoodlescompose.ui.currentlyPlaying.CurrentlyPlayingScreen
import com.example.musicbynoodlescompose.ui.currentlyPlaying.SliderState
import com.example.musicbynoodlescompose.ui.playlists.AddToPlaylistScreen
import com.example.musicbynoodlescompose.ui.playlists.CurrentPlaylist
import com.example.musicbynoodlescompose.ui.playlists.PlaylistsLibrary
import com.example.musicbynoodlescompose.ui.songs.SongsLibrary
import com.example.musicbynoodlescompose.ui.theme.MusicByNoodlesComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

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
                var playerState: PlayerState? by remember {
                    mutableStateOf(mediaController?.state())
                }

                DisposableEffect(key1 = mediaController) {
                    mediaController?.run {
                        playerState = state()
                    }
                    onDispose {
                        playerState?.dispose()
                    }
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
                var sleepTimerSet by remember { mutableStateOf(false) }
                var sleepTimer by remember { mutableStateOf(Duration.ZERO) }
                LaunchedEffect(key1 = sleepTimerSet) {
                    delay(sleepTimer)
                    if (sleepTimerSet) {
                        mediaController?.stop()
                    }
                }

                val mediaContentViewModel = hiltViewModel<MediaContentViewModel>()
                val playlistsViewModel = hiltViewModel<PlaylistsViewModel>()

                val navController = rememberNavController()
                val navigationItems = bottomNavigationItems()
                var selectedItemIndex by remember { mutableIntStateOf(3) }

                Box(
                    modifier = Modifier//.safeDrawingPadding()
                        .fillMaxSize()
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
                    Scaffold(
                        modifier = Modifier
                            .background(Color.Transparent),
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
                        Column(
                            modifier = Modifier
                                .padding(scaffoldPadding)
                        ) {
                            NavigationHost(
                                modifier = Modifier
                                    .weight(1f),
                                navController = navController,
                                mediaContent = mediaContentViewModel.media.value,
                                playerState = playerState,
                                sliderState = sliderState.state,
                                setSlider = sliderState::setSlider,
                                setPlaylist = { chosenPlaylist, chosenPosition ->
                                    mediaController?.setPlaylistWithIndex(chosenPlaylist, chosenPosition)
                                },
                                playerAction = { playerAction ->
                                    mediaController?.playerAction(playerAction)
                                },
                                playlists = playlistsViewModel.playlists.value,
                                addSongToQueue = {
                                    mediaController?.addToQueue(listOf(it))
                                },
                                addSongsToQueue = {
                                    mediaController?.addToQueue(it)
                                },
                                onAddToPlaylistSelected = playlistsViewModel::updatePlaylist,
                                onDeletePlaylist = playlistsViewModel::deletePlaylist,
                                removeFromPlaylist = playlistsViewModel::removeFromPlaylist,
                                onSleepTimerSelected = {
                                    isSleepDialogOpen = true
                                }
                            )
                            val playingBarVisible =
                                (mediaController?.isPlaying == true || mediaController?.currentMediaItem != null) &&
                                        navController.currentDestination?.route != Destination.CurrentlyPlaying.route

                            CurrentlyPlayingBar(
                                visible = playingBarVisible,
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

                when {
                    isSleepDialogOpen -> {
                        AlertDialog(
                            title = { Text("Sleep timer") },
                            text = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "15 minutes",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    sleepTimerSet = true
                                                    sleepTimer = 15.minutes
                                                    isSleepDialogOpen = false
                                                    Toast.makeText(applicationContext, "Timer set for 15 minutes", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "30 minutes",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    sleepTimerSet = true
                                                    sleepTimer = 30.minutes
                                                    isSleepDialogOpen = false
                                                    Toast.makeText(applicationContext, "Timer set for 30 minutes", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "45 minutes",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    sleepTimerSet = true
                                                    sleepTimer = 45.minutes
                                                    isSleepDialogOpen = false
                                                    Toast.makeText(applicationContext, "Timer set for 45 minutes", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "1 hour",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    sleepTimerSet = true
                                                    sleepTimer = 60.minutes
                                                    isSleepDialogOpen = false
                                                    Toast.makeText(applicationContext, "Timer set for 1 hour", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "1.5 hours",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    sleepTimerSet = true
                                                    sleepTimer = 90.minutes
                                                    isSleepDialogOpen = false
                                                    Toast.makeText(applicationContext, "Timer set for 1.5 hours", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "2 hours",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    sleepTimerSet = true
                                                    sleepTimer = 120.minutes
                                                    isSleepDialogOpen = false
                                                    Toast.makeText(applicationContext, "Timer set for 2 hours", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                    )
                                    if (sleepTimerSet) {
                                        Spacer(modifier = Modifier.height(15.dp))
                                        Text(
                                            text = "Cancel timer",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier
                                                .clickable(
                                                    onClick = {
                                                        sleepTimerSet = false
                                                        isSleepDialogOpen = false
                                                        Toast.makeText(applicationContext, "Sleep timer cancelled", Toast.LENGTH_SHORT).show()
                                                    }
                                                )
                                        )
                                    }
                                }
                            },
                            onDismissRequest = {
                                isSleepDialogOpen = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        isSleepDialogOpen = false
                                    }
                                ) {
                                    Text(
                                        text = "Dismiss",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    listState: LazyListState,
    searchValue: String,
    onValueChange: (value: String) -> Unit,
    onSleepTimerSelected: () -> Unit
) {
    var searchExpanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val density = LocalDensity.current

    LaunchedEffect(key1 = listState.isScrollInProgress) {
        if (listState.isScrollInProgress) keyboardController?.hide()
    }
    LaunchedEffect(key1 = searchExpanded) {
        if (searchExpanded) focusRequester.requestFocus()
    }

    Row(
        modifier = Modifier
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (listState.isScrolled) 0.dp else 130.dp)
    ) {}
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent)
            .padding(horizontal = 25.dp)
            .padding(top = 10.dp)
            .padding(bottom = if (listState.isScrolled) 10.dp else 15.dp),
    ) {
        AnimatedVisibility(
            visible = !searchExpanded,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) {
                // Slide in from 40 dp from the top.
                with(density) { -300.dp.roundToPx() }
            } + expandHorizontally(
                // Expand from the top.
                expandFrom = Alignment.Start,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 500)) {
                with(density) { -300.dp.roundToPx() }
            } + shrinkHorizontally(
                shrinkTowards = Alignment.Start,
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            Text(
                text = title,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
                AnimatedVisibility(
                    visible = searchExpanded,
                    modifier = Modifier
                        .weight(1f)//.offset(x = (25).dp),
                            ,
                    enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) {
                        // Slide in from 40 dp from the top.
                        with(density) { 300.dp.roundToPx() }
                    } + expandHorizontally(
                        // Expand from the top.
                        expandFrom = Alignment.End,
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutHorizontally(animationSpec = tween(durationMillis = 500)) {
                        with(density) { 300.dp.roundToPx() }
                    } + shrinkHorizontally(
                        shrinkTowards = Alignment.End,
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    BasicTextField(
                        value = searchValue,
                        onValueChange = onValueChange,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(end = 10.dp),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                value = searchValue,
                                innerTextField = innerTextField,
                                enabled = true,
                                placeholder = { Text("Search") },
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = remember { MutableInteractionSource() },
                                contentPadding = PaddingValues(bottom = 10.dp, end = 30.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                                    focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                                )
                            )
                        }
                    )
//                TextField(
//                    value = searchValue,
//                    onValueChange = onValueChange,
//                    textStyle = MaterialTheme.typography.bodyLarge,
//                    maxLines = 1,
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color.Transparent,
//                        unfocusedContainerColor = Color.Transparent,
//                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
//                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
//                        focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
//                        focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary
//                    )
//                )
                }
                Icon(
                    imageVector = if (searchExpanded) {
                        Icons.Outlined.Close
                    } else {
                        Icons.Outlined.Search
                    },
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                searchExpanded = !searchExpanded
                            }
                        )
                )

            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clickable(
                        onClick = onSleepTimerSelected
                    )
            )
        }

    }
}

@Composable
fun CurrentlyPlayingBar(
    visible: Boolean,
    playerState: PlayerState?,
    sliderState: SliderState,
    navToCurrentlyPlayingScreen: () -> Unit,
    playPauseAction: () -> Unit
) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .animateContentSize(animationSpec = tween(durationMillis = 500))
                .fillMaxWidth()
                .height(if (visible) 70.dp else 0.dp)
                .padding(horizontal = 30.dp)
                .padding(top = 10.dp)
                .clickable(
                    onClick = {
                        navToCurrentlyPlayingScreen()
                    }
                ),
        ) {
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = playerState
                            ?.currentMediaItem
                            ?.mediaMetadata
                            ?.artworkUri,
                        builder = {
                            placeholder(R.drawable.artwork_placeholder)
                            error(R.drawable.artwork_placeholder)
                        }
                    ),
                    contentDescription = "Album artwork",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 10.dp)
                        .height(50.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = playerState
                                ?.currentMediaItem
                                ?.mediaMetadata
                                ?.title
                                .toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = playerState
                                ?.currentMediaItem
                                ?.mediaMetadata
                                ?.artist
                                .toString(),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    SongProgressIndicator(
                        progress = sliderState.linearProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                        //.alpha(if (visible) 1f else 0f)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    imageVector = if (playerState?.isPlaying == true) {
                        Icons.Default.Pause
                    } else {
                        Icons.Default.PlayArrow
                    },
                    contentDescription = "Play",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(
                        onClick = {
                            playPauseAction()
                        }
                    )
                )
            }
        }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermissions(activity: Activity) {
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
    navController: NavHostController,
    mediaContent: MediaContent,
    playlists: List<Playlist>,
    playerState: PlayerState?,
    sliderState: SliderState,
    setPlaylist: (List<Song>, Int) -> Unit,
    onAddToPlaylistSelected: (playlist: Playlist) -> Unit,
    removeFromPlaylist: (playlist: Playlist, index: Int) -> Unit,
    onDeletePlaylist: (playlist: Playlist) -> Unit,
    addSongToQueue: (song: Song) -> Unit,
    addSongsToQueue: (songs: List<Song>) -> Unit,
    playerAction: (PlayerAction) -> Unit,
    setSlider: (position: Long) -> Unit,
    onSleepTimerSelected: () -> Unit,
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
                    onDeletePlaylist = onDeletePlaylist,
                    listState = listStates.playlistsState.value,
                    onSleepTimerSelected = onSleepTimerSelected
                )
            }
            composable(
                route = Destination.CurrentPlaylist.route
            ) {
                CurrentPlaylist(
                    playlist = navigationViewModel.currentPlaylist,
                    onSongSelected = { playlist, index ->
                        setPlaylist(playlist, index)
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
                    addToQueue = addSongToQueue,
                    removeFromPlaylist = removeFromPlaylist
                )
            }
            composable(
                route = Destination.AddToPlaylist.route
            ) {
                AddToPlaylistScreen(
                    songsToAdd = navigationViewModel.songToAdd,
                    playlistsLibrary = playlists,
                    onAddToPlaylistSelected = { playlist ->
                        onAddToPlaylistSelected(playlist)
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
                ArtistsLibrary(
                    artistsLibrary = mediaContent.artistsAlphabetical,
                    onArtistSelected = { selectedArtist ->
                        navigationViewModel.currentArtist = selectedArtist
                        navController.navigate(Destination.CurrentArtist.route)
                    },
                    listState = listStates.artistsState.value,
                    onSleepTimerSelected = onSleepTimerSelected
                )
            }
            composable(
                route = Destination.AlbumsLibrary.route,
                enterTransition = TransitionAnimation.AlbumsLibrary.enterTransition,
                exitTransition = TransitionAnimation.AlbumsLibrary.exitTransition
            ) {
                AlbumsLibrary(
                    albumsLibrary = mediaContent.albumsAlphabetical,
                    onAlbumSelected = { selectedAlbum ->
                        navigationViewModel.currentAlbum = selectedAlbum
                        navController.navigate(Destination.CurrentAlbum.route)
                    },
                    onArtistSelected = { selectedArtistId ->
                        navigationViewModel.currentArtist = mediaContent.artistsMap[selectedArtistId]!!
                        navController.navigate(Destination.CurrentArtist.route)
                    },
                    addToPlaylist = { songsToAdd ->
                        navigationViewModel.songToAdd = songsToAdd
                        navController.navigate(Destination.AddToPlaylist.route)
                    },
                    addToQueue = addSongsToQueue,
                    listState = listStates.albumsState.value,
                    onSleepTimerSelected = onSleepTimerSelected
                )
            }
            composable(
                route = Destination.SongsLibrary.route,
                enterTransition = TransitionAnimation.SongsLibrary.enterTransition,
                exitTransition = TransitionAnimation.SongsLibrary.exitTransition
            ) {
                SongsLibrary(
                    songsLibrary = mediaContent.songsAlphabetical,
                    onSongSelected = { selectedSong ->
                        setPlaylist(mediaContent.songsAlphabetical, selectedSong)
                        navController.navigate(Destination.CurrentlyPlaying.route)
                    },
                    onAlbumSelected = { selectedAlbumId ->
                        navigationViewModel.currentAlbum = mediaContent.albumsMap[selectedAlbumId]!!
                        navController.navigate(Destination.CurrentAlbum.route)
                    },
                    onArtistSelected = { selectedArtistId ->
                        navigationViewModel.currentArtist = mediaContent.artistsMap[selectedArtistId]!!
                        navController.navigate(Destination.CurrentArtist.route)
                    },
                    addSongToPlaylist = { selectedSong ->
                        navigationViewModel.songToAdd = listOf(selectedSong)
                        navController.navigate(Destination.AddToPlaylist.route)
                    },
                    addToQueue = addSongToQueue,
                    listState = listStates.songsState.value,
                    onSleepTimerSelected = onSleepTimerSelected
                )
            }
            composable(
                route = Destination.CurrentArtist.route,
                enterTransition = TransitionAnimation.CurrentArtist.enterTransition,
                exitTransition = TransitionAnimation.CurrentArtist.exitTransition
            ) {
                CurrentArtist(
                    artist = navigationViewModel.currentArtist,
                    onAlbumSelected = { selectedAlbum ->
                        navigationViewModel.currentAlbum = selectedAlbum
                        navController.navigate(Destination.CurrentAlbum.route)
                    },
                    addToQueue = addSongsToQueue,
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
                CurrentAlbum(
                    album = navigationViewModel.currentAlbum,
                    onSongSelected = { selectedSong ->
                        setPlaylist(navigationViewModel.currentAlbum.songs, selectedSong)
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
                    addToQueue = addSongToQueue
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
                        playerAction(it)
                    },
                    setSlider = setSlider
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navigate: (String) -> Unit,
    bottomNavigationItems: List<BottomNavigationItem>,
    selectedItemIndex: Int,
    selectedItemIndexChange: (index: Int) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
        containerColor = MaterialTheme.colorScheme.onBackground
    ) {
        bottomNavigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.background(Color.Transparent),
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndexChange(index)
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
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

fun bottomNavigationItems(): List<BottomNavigationItem> {
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