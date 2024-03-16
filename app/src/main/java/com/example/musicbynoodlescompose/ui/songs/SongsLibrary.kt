package com.example.musicbynoodlescompose.ui.songs

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.data.Song

@Composable
fun SongsLibrary(
    songsLibrary: List<Song>,
    onSongSelected: (index: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(start = 15.dp, top = 10.dp, end = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        itemsIndexed(songsLibrary) { index, song ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(65.dp)
                    .padding(15.dp, 0.dp, 15.dp, 0.dp)
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onSongSelected(index)
                        }
                    )
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = song.imageUri,
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
                        text = song.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = song.artist,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
//                Image(
//                    painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_menu_overflow_material),
//                    contentDescription = null
//                )

            }
            if (index != songsLibrary.size - 1) {
                Divider(
                    Modifier
                        .padding(15.dp, 0.dp)
                )
            }
        }
    }
}