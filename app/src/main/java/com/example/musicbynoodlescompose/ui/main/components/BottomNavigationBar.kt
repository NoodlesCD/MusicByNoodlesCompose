package com.example.musicbynoodlescompose.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.musicbynoodlescompose.ui.misc.BottomNavigationItem

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