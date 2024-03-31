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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.TopBar
import com.example.musicbynoodlescompose.data.Artist
import com.example.musicbynoodlescompose.ui.misc.ListMenuItem

@Composable
fun ArtistsLibrary(
    artistsLibrary: List<Artist>,
    onArtistSelected: (artist: Artist) -> Unit,
    listState: LazyListState,
){
    var searchValue by remember { mutableStateOf("") }
    var searchedList by remember { mutableStateOf(artistsLibrary) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = "Artists",
            listState = listState,
            searchValue = searchValue,
            onValueChange = {
                searchValue = it
                searchedList = artistsLibrary.filter { artist ->
                    artist.name.lowercase().contains(searchValue.lowercase())
                }
            }
        )
        LazyColumn(
            state = listState,
        modifier = Modifier.fillMaxSize()
            .padding(start = 15.dp, top = 10.dp, end = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
            itemsIndexed(
                searchedList
//                if (searchValue.isNotBlank()) {
//                    searchedList
//                } else {
//                    artistsLibrary
//                }
            ) { index, artist ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(15.dp, 0.dp, 15.dp, 0.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onArtistSelected(artist)
                            }
                        )
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
                        Modifier.size(45.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(15.dp, 0.dp, 0.dp, 0.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (artist.songCount > 1) "${artist.songCount} songs" else "1 song",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (index != artistsLibrary.size - 1) {
                    Divider(
                        Modifier
                            .padding(15.dp, 0.dp)
                    )
                }
            }
        }
    }
}