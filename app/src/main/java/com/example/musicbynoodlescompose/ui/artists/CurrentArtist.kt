package com.example.musicbynoodlescompose.ui.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.data.Album
import com.example.musicbynoodlescompose.data.Artist

@Composable
fun CurrentArtist(
    artist: Artist,
    onAlbumSelected: (album: Album) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp, 20.dp, 15.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp, 15.dp, 15.dp, 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(
                    data = artist.albums[0].albumUri,
                    builder = {
                        placeholder(R.drawable.artwork_placeholder)
                        error(R.drawable.artwork_placeholder)
                    }
                ),
                contentDescription = "Album artwork",
                modifier = Modifier.size(70.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Column(
                modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text =
                    if (artist.albums.size > 1) { "${artist.albums.size} albums" } else { "1 album" }
                            + " · "
                            + if (artist.songCount > 1) { "${artist.songCount} songs" } else { "1 song" },
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            itemsIndexed(artist.albums) { index, album ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(15.dp, 0.dp, 15.dp, 0.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onAlbumSelected(album)
                            }
                        )
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = album.albumUri,
                            builder = {
                                placeholder(R.drawable.artwork_placeholder)
                                error(R.drawable.artwork_placeholder)
                            }
                        ),
                        contentDescription = "Album artwork",
                        Modifier.size(45.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(15.dp, 2.dp, 0.dp, 0.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = album.title,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
//                    Image(
//                        painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_menu_overflow_material),
//                        contentDescription = null
//                    )

                }
                if (index != artist.albums.size - 1) {
                    Divider(
                        Modifier
                            .padding(20.dp, 0.dp)
                    )
                }
            }
        }
    }
}