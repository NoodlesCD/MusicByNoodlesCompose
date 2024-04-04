package com.example.musicbynoodlescompose.ui.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.musicbynoodlescompose.R
import com.example.musicbynoodlescompose.ui.core.components.ListDropdownMenu
import com.example.musicbynoodlescompose.ui.core.components.ListMenuItem

@Composable
fun LibraryListRow(
    rowImage: String,
    primaryText: String,
    secondaryText: String,
    isFinalRow: Boolean,
    dropdownMenuItems: List<ListMenuItem>? = null,
    onRowSelected: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(65.dp)
            .padding(15.dp, 0.dp, 15.dp, 0.dp)
            .fillMaxWidth()
            .clickable(
                onClick = onRowSelected
            )
    ) {
        RowImage(image = rowImage)
        RowText(
            modifier = Modifier.weight(1f),
            primaryText = primaryText,
            secondaryText = secondaryText
        )
        if (dropdownMenuItems != null) {
            ListDropdownMenu(
                menuItems = dropdownMenuItems
            )
        }
    }
    if (!isFinalRow) {
        Divider(
            Modifier
                .padding(15.dp, 0.dp)
        )
    }
}

@Composable
internal fun RowImage(
    image: String,
) {
    Image(
        contentDescription = "Album artwork",
        painter = rememberImagePainter(
            data = image,
            builder = {
                placeholder(R.drawable.artwork_placeholder)
                error(R.drawable.artwork_placeholder)
            }
        ),
        modifier = Modifier
            .size(45.dp)
            .clip(RoundedCornerShape(4.dp))
    )
}

@Composable
internal fun RowText(
    modifier: Modifier,
    primaryText: String,
    secondaryText: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(15.dp, 2.dp, 0.dp, 0.dp)
            .then(modifier)
    ) {
        Text(
            text = primaryText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = secondaryText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}