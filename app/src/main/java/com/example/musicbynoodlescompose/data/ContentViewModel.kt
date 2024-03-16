package com.example.musicbynoodlescompose.data

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class ContentViewModel: ViewModel() {

    var artistsMap = mutableStateMapOf<Long, Artist>()
    val albumsMap = mutableStateMapOf<Long, Album>()
    val songsMap = mutableStateMapOf<Long, Song>()
    val songsList = mutableStateListOf<Song>()

    var artistsAlphabetical = mutableStateListOf<Artist>()
    var albumsAlphabetical = mutableStateListOf<Album>()
    var songsAlphabetical = mutableStateListOf<Song>()

    private var initialized = false

    fun initialize(context: Context) {
        if (initialized) return

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)

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
                    val imgUri: Uri = ContentUris.withAppendedId(
                        sArtworkUri,
                        albumId
                    )

                    val newSong = Song(
                        id,
                        title,
                        album,
                        artist,
                        cdTrackNumber,
                        albumId,
                        artistId,
                        duration,
                        trackUri,
                        imgUri
                    )

                    // If the artist does not already exist in the list, create one.
                    if (artistsMap[artistId] == null) {
                        val newAlbum = mutableListOf(
                            Album(
                                album,
                                artist,
                                artistId,
                                mutableListOf(newSong),
                                imgUri
                            )
                        )
                        artistsMap[artistId] = Artist(artist, newAlbum, 1)
                    }
                    // If the artist already exists.
                    else {
                        var existingAlbum = false

                        // Check if album exists
                        for (i in artistsMap.getValue(artistId).albums.indices) {
                            if (artistsMap.getValue(artistId).albums[i].title == album) {
                                artistsMap.getValue(artistId).albums[i].songs.add(newSong)
                                existingAlbum = true
                                break
                            }
                        }

                        if (!existingAlbum) {
                            val newAlbum =
                                Album(album, artist, artistId, mutableListOf(newSong), imgUri)
                            artistsMap.getValue(artistId).albums.add(newAlbum)
                        }

                        artistsMap.getValue(artistId).songCount++
                    }

                    if (albumsMap[albumId] == null) {
                        albumsMap[albumId] =
                            Album(album, artist, artistId, mutableListOf(newSong), imgUri)
                    } else {
                        albumsMap[albumId]?.songs?.add(newSong)
                    }

                    songsList.add(newSong)
                    songsMap[id] = newSong
                } while (cursor.moveToNext())

            }
        }
        cursor?.close()

        if (songsList.isNotEmpty()) {
            artistsAlphabetical = ArrayList<Artist>(artistsMap.values).sortedBy { it.name }.toMutableStateList()
            albumsAlphabetical = ArrayList<Album>(albumsMap.values).sortedBy { it.title }.toMutableStateList()
            songsAlphabetical = songsList.sortedBy { it.title }.toMutableStateList()
        }
    }
}