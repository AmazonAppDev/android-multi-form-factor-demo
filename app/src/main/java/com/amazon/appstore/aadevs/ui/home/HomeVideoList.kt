package com.amazon.appstore.aadevs.ui.home

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.ui.modifiers.interceptKey
import com.amazon.appstore.aadevs.ui.rememberContentPaddingForScreen
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme
import com.amazon.appstore.aadevs.ui.theme.AmazonOrange
import com.amazon.appstore.aadevs.ui.video.shareVideo
import com.prof.youtubeparser.models.videos.Video

@Composable
fun HomeVideoList(
    uiState: HomeUiState,
    showTopAppBar: Boolean,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    searchInput: String = "",
    onSelectVideo: (String) -> Unit,
    onSearchInputChanged: (String) -> Unit,
) {
    HomeScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        openSearch = openSearch,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier
    ) { hasVideosUiState, contentModifier ->
        VideoList(
            videosFeed = hasVideosUiState.videosFeed,
            showExpandedSearch = !showTopAppBar,
            contentPadding = rememberContentPaddingForScreen(
                additionalTop = if (showTopAppBar) 0.dp else 8.dp
            ),
            modifier = contentModifier,
            state = homeListLazyListState,
            searchInput = searchInput,
            onVideoTapped = onSelectVideo,
            onSearchInputChanged = onSearchInputChanged
        )
    }
}

@Composable
fun VideoList(
    videosFeed: List<Video>,
    showExpandedSearch: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState(),
    searchInput: String = "",
    onVideoTapped: (String) -> Unit,
    onSearchInputChanged: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state
    ) {
        if (showExpandedSearch) {
            item {
                HomeSearch(
                    Modifier.padding(horizontal = 16.dp),
                    searchInput = searchInput,
                    onSearchInputChanged = onSearchInputChanged,
                )
            }
        }
        item { VideosListSection(videosFeed, onVideoTapped) }
    }
}

@Composable
private fun VideosListSection(
    videos: List<Video>,
    navigateToVideo: (String) -> Unit
) {
    Column {
        videos.forEach { video ->
            VideoItem(video, navigateToVideo)
            VideoListDivider()
        }
    }
}

@Composable
private fun VideoListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = AmazonOrange
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeSearch(
    modifier: Modifier = Modifier,
    searchInput: String = "",
    onSearchInputChanged: (String) -> Unit,
) {
    val openDialog = remember { mutableStateOf(true) }

    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(Dp.Hairline, MaterialTheme.colors.onSurface.copy(alpha = .6f)),
        elevation = 4.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                IconButton(onClick = { Log.w("HomeVideoList", "Not implemented") }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.cd_search)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                val context = LocalContext.current
                val focusManager = LocalFocusManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            openDialog.value = false
                        },
                        title = {
                            Text(text = "Search")
                        },
                        text = {
                            Column() {
                                TextField(
                                    value = "",
                                    onValueChange = { var text = it }
                                )
                                Text("Text to Search")
                                Checkbox(checked = false, onCheckedChange = {})
                            }
                        },
                        buttons = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { openDialog.value = false }
                                ) {
                                    Text("Dismiss")
                                }
                            }
                        }
                    )
                }
                TextField(
                    value = searchInput,
                    onValueChange = { onSearchInputChanged(it) },
                    placeholder = { Text(stringResource(R.string.home_search)) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            submitSearch(searchInput, onSearchInputChanged, context)
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .interceptKey(Key.F) {
                            openDialog.value = true
                        }
                        .interceptKey(Key.Enter) {
                            submitSearch(searchInput, onSearchInputChanged, context)
                        }
                        .interceptKey(Key.Escape) {
                            focusManager.clearFocus()
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { TODO() }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(R.string.cd_more_actions)
                    )
                }
            }
        }
    }
}

private fun submitSearch(
    searchInput: String,
    onSearchInputChanged: (String) -> Unit,
    context: Context
) {
    onSearchInputChanged("")
    Toast.makeText(
        context,
        "- $searchInput - Search is not yet implemented",
        Toast.LENGTH_SHORT
    ).show()
}

@Preview("Home list drawer screen")
@Preview("Home list drawer screen (dark)", uiMode = UI_MODE_NIGHT_YES)
@Preview("Home list drawer screen (big font)", fontScale = 1.5f)
@Composable
fun PreviewHomeListDrawerScreen() {
    AADevsTheme {
        HomeVideoList(
            uiState = HomeUiState.HasVideos(
                videosFeed = emptyList(),
                selectedVideo = Video(),
                isVideoOpen = false,
                isLoading = false,
                errorMessages = emptyList(),
                searchInput = ""
            ),
            showTopAppBar = false,
            onErrorDismiss = {},
            openDrawer = {},
            openSearch = {},
            homeListLazyListState = rememberLazyListState(),
            scaffoldState = rememberScaffoldState(),
            onSelectVideo = {},
            onSearchInputChanged = {}
        )
    }
}