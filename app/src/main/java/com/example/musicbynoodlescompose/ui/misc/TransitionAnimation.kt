package com.example.musicbynoodlescompose.ui.misc

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

sealed class TransitionAnimation(
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?,
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
) {

    /**
     *  PLAYLISTS LIBRARY
     *
     */
    data object PlaylistsLibrary: TransitionAnimation(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (initialState.destination.route) {
                Destination.ArtistsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.AlbumsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.SongsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
            }
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (targetState.destination.route) {
                Destination.ArtistsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.AlbumsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.SongsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
            }
        }
    )


    /**
     *  ARTISTS LIBRARY
     *
     */
    data object ArtistsLibrary: TransitionAnimation(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (initialState.destination.route) {
                Destination.PlaylistsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.AlbumsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.SongsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
            }
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (targetState.destination.route) {
                Destination.PlaylistsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.AlbumsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.SongsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
            }
        }
    )


    /**
     *  ALBUMS LIBRARY
     *
     */
    data object AlbumsLibrary: TransitionAnimation(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (initialState.destination.route) {
                Destination.PlaylistsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.ArtistsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.SongsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
            }
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (targetState.destination.route) {
                Destination.PlaylistsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.ArtistsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.SongsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
            }
        }
    )


    /**
     *  SONGS LIBRARY
     *
     */
    data object SongsLibrary: TransitionAnimation(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (initialState.destination.route) {
                Destination.PlaylistsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.ArtistsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                Destination.AlbumsLibrary.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
            }
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (targetState.destination.route) {
                Destination.PlaylistsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.ArtistsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                Destination.AlbumsLibrary.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
            }
        }
    )


    /**
     *  CURRENT ARTIST
     *
     */
    data object CurrentArtist: TransitionAnimation(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (initialState.destination.route) {
                Destination.CurrentlyPlaying.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
                Destination.CurrentAlbum.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
            }
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (targetState.destination.route) {
                Destination.CurrentlyPlaying.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
                Destination.CurrentAlbum.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
            }
        }
    )


    /**
     *  CURRENT ALBUM
     *
     */
    data object CurrentAlbum: TransitionAnimation(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (initialState.destination.route) {
                Destination.CurrentlyPlaying.route -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }
            }
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + when (targetState.destination.route) {
                Destination.CurrentlyPlaying.route -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(600)
                    )
                }

                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(600)
                    )
                }
            }
        }
    )


    /**
     *  CURRENTLY PLAYING
     *
     */
    data object CurrentlyPlaying: TransitionAnimation(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(600)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    400, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(600)
            )
        }
    )
}