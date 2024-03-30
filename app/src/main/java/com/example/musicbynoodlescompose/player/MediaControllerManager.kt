package com.example.musicbynoodlescompose.player

import android.content.ComponentName
import android.content.Context
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.mutableStateOf
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.MoreExecutors
import com.google.common.util.concurrent.ListenableFuture

internal class MediaControllerManager private constructor(context: Context) : RememberObserver {

    private val appContext = context.applicationContext
    private var factory: ListenableFuture<MediaController>? = null
    var controller = mutableStateOf<MediaController?>(null)
        private set

    init { initialize() }

    internal fun initialize() {
        if (factory == null || factory?.isDone == true) {
            factory = MediaController.Builder(
                appContext,
                SessionToken(
                    appContext,
                    ComponentName(appContext, PlaybackService::class.java)
                )
            ).buildAsync()
        }
        factory?.addListener(
            {
                // MediaController is available here with controllerFuture.get()
                controller.value = factory?.let {
                    if (it.isDone)
                        it.get()
                    else
                        null
                }
            },
            MoreExecutors.directExecutor()
        )
    }

    internal fun release() {
        factory?.let {
            MediaController.releaseFuture(it)
            controller.value = null
        }
        factory = null
    }

    override fun onAbandoned() {}
    override fun onForgotten() {}
    override fun onRemembered() {}

    companion object {
        @Volatile
        private var instance: MediaControllerManager? = null

        fun getInstance(context: Context): MediaControllerManager {
            return instance ?: synchronized(this) {
                instance ?: MediaControllerManager(context).also { instance = it }
            }
        }
    }
}