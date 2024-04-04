package com.example.musicbynoodlescompose.ui.library.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicbynoodlescompose.isScrolled
import com.example.musicbynoodlescompose.ui.library.Library

/**
 * Component for the top bar/header in the [Library] component.
 *
 * @param title The title to be shown in the header of the library.
 * @param listState The state of the list in [LibraryList]. The items in the list will be updated
 * based on the [searchValue].
 * @param searchValue Holds the current value of the search box.
 * @param onValueChange Updates the current [searchValue]
 * @param onSleepTimerSelected Passes sleep timer up the composable chain.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryTopBar(
    title: String,
    listState: LazyListState,
    searchValue: String,
    onValueChange: (value: String) -> Unit,
    onSleepTimerSelected: () -> Unit
) {
    var searchExpanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val density = LocalDensity.current

    LaunchedEffect(key1 = listState.isScrollInProgress) {
        if (listState.isScrollInProgress) keyboardController?.hide()
    }
    LaunchedEffect(key1 = searchExpanded) {
        if (searchExpanded) focusRequester.requestFocus()
    }

    Spacer( modifier = Modifier
        .animateContentSize(animationSpec = tween(durationMillis = 300))
        .height(height = if (listState.isScrolled) 0.dp else 140.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent)
            .padding(horizontal = 25.dp)
            .padding(top = 10.dp)
            .padding(bottom = if (listState.isScrolled) 10.dp else 15.dp),
    ) {
        AnimatedVisibility(
            visible = !searchExpanded,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) {
                with(density) { -300.dp.roundToPx() }
            } + expandHorizontally(
                expandFrom = Alignment.Start,
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 500)) {
                with(density) { -300.dp.roundToPx() }
            } + shrinkHorizontally(
                shrinkTowards = Alignment.Start,
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            Text(
                text = title,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            AnimatedVisibility(
                visible = searchExpanded,
                modifier = Modifier
                    .weight(1f)//.offset(x = (25).dp),
                ,
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) {
                    // Slide in from 40 dp from the top.
                    with(density) { 300.dp.roundToPx() }
                } + expandHorizontally(
                    // Expand from the top.
                    expandFrom = Alignment.End,
                    animationSpec = tween(durationMillis = 500)
                ),
                exit = slideOutHorizontally(animationSpec = tween(durationMillis = 500)) {
                    with(density) { 300.dp.roundToPx() }
                } + shrinkHorizontally(
                    shrinkTowards = Alignment.End,
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                LibrarySearchTextField(
                    value = searchValue,
                    onValueChange = onValueChange,
                    focusRequester = focusRequester
                )
            }

            SearchIcon(
                isSearchExpanded = searchExpanded,
                setIsSearchExpanded = { searchExpanded = it }
            )

            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clickable(
                        onClick = onSleepTimerSelected
                    )
            )
        }
    }
}

@Composable
fun SearchIcon(
    isSearchExpanded: Boolean,
    setIsSearchExpanded: (isExpanded: Boolean) -> Unit
) {
    Icon(
        imageVector = if (isSearchExpanded) {
            Icons.Outlined.Close
        } else {
            Icons.Outlined.Search
        },
        contentDescription = "Search",
        tint = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .clickable(
                onClick = {
                    setIsSearchExpanded(!isSearchExpanded)
                }
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrarySearchTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    focusRequester: FocusRequester,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(end = 10.dp),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                placeholder = { Text("Search") },
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = remember { MutableInteractionSource() },
                contentPadding = PaddingValues(bottom = 10.dp, end = 30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    )
}