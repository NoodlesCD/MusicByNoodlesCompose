package com.example.musicbynoodlescompose.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun ListDropdownMenu(
    menuItems: List<ListMenuItem>
) {
    Box {
        var isMenuExpanded by remember { mutableStateOf(false) }

        Image(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier
                .clickable(
                    onClick = {
                        isMenuExpanded = !isMenuExpanded
                    }
                )
        )
        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false }
        ) {
            for (menuItem in menuItems) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = menuItem.text,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = {
                        menuItem.onClick()
                        isMenuExpanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        disabledTextColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

data class ListMenuItem(
    val text: String,
    val onClick: () -> Unit,
)