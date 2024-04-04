package com.example.musicbynoodlescompose.data

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.musicbynoodlescompose.data.models.Album
import com.example.musicbynoodlescompose.data.models.Artist
import com.example.musicbynoodlescompose.data.models.MediaContent
import com.example.musicbynoodlescompose.data.models.Song
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MediaResolver @Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val usersMedia = MediaContent(mutableMapOf(), mutableMapOf(), listOf(), listOf(), listOf())
    private val unsortedSongsList = mutableStateListOf<Song>()

    init {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = appContext.contentResolver.query(
            uri, null, null, null, null
        )

        when {
            cursor == null -> Log.e("ContentManagement.kt", "ContentResolver query failed.")
            !cursor.moveToFirst() -> Log.i(
                "ContentManagement.kt",
                "ContentResolver failed to find any media on device."
            )
            else -> {
                val idColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val titleColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val albumColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                val artistColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val albumIdColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
                val artistIdColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)
                val durationColumn: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val cdTrackNumberColumn: Int =
                    cursor.getColumnIndex(MediaStore.Audio.Media.CD_TRACK_NUMBER)

                do {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val album = cursor.getString(albumColumn)
                    val artist = cursor.getString(artistColumn)
                    val cdTrackNumber = cursor.getInt(cdTrackNumberColumn)
                    val albumId = cursor.getLong(albumIdColumn)
                    val artistId = cursor.getLong(artistIdColumn)
                    val duration = cursor.getInt(durationColumn)
                    val trackUri =
                        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

                    val sArtworkUri: Uri = Uri.parse("content://media/external/audio/albumart")
                    val imgUri = ContentUris.withAppendedId(
                        sArtworkUri,
                        albumId
                    ).toString()

                    val newSong = Song(
                        id,
                        title,
                        album,
                        artist,
                        cdTrackNumber,
                        albumId,
                        artistId,
                        duration,
                        trackUri.toString(),
                        imgUri
                    )

                    // If the artist does not already exist in the list, create one.
                    if (usersMedia.artistsMap[artistId] == null) {
                        val newAlbum = mutableListOf(
                            Album(
                                album,
                                artist,
                                artistId,
                                mutableListOf(newSong),
                                imgUri
                            )
                        )
                        usersMedia.artistsMap[artistId] = Artist(artist, newAlbum, 1)
                    }
                    // If the artist already exists.
                    else {
                        var existingAlbum = false

                        // Check if album exists
                        for (i in usersMedia.artistsMap.getValue(artistId).albums.indices) {
                            if (usersMedia.artistsMap.getValue(artistId).albums[i].title == album) {
                                usersMedia.artistsMap.getValue(artistId).albums[i].songs.add(newSong)
                                existingAlbum = true
                                break
                            }
                        }

                        if (!existingAlbum) {
                            val newAlbum =
                                Album(album, artist, artistId, mutableListOf(newSong), imgUri)
                            usersMedia.artistsMap.getValue(artistId).albums.add(newAlbum)
                        }

                        usersMedia.artistsMap.getValue(artistId).songCount++
                    }

                    if (usersMedia.albumsMap[albumId] == null) {
                        usersMedia.albumsMap[albumId] =
                            Album(album, artist, artistId, mutableListOf(newSong), imgUri)
                    } else {
                        usersMedia.albumsMap[albumId]?.songs?.add(newSong)
                    }

                    unsortedSongsList.add(newSong)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()

        if (unsortedSongsList.isNotEmpty()) {
            usersMedia.artistsAlphabetical = ArrayList<Artist>(usersMedia.artistsMap.values).sortedBy { it.name }.toList()
            usersMedia.albumsAlphabetical = ArrayList<Album>(usersMedia.albumsMap.values).sortedBy { it.title }.toList()
            usersMedia.songsAlphabetical = unsortedSongsList.sortedBy { it.title }.toList()
        }
    }

    fun getMediaContent(): MediaContent {
        return usersMedia
    }
}