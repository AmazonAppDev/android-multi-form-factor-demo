// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0


package com.amazon.appstore.aadevs.ui.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amazon.appstore.aadevs.ui.ShareButton
import com.amazon.appstore.aadevs.ui.modifiers.notifyInput
import com.amazon.appstore.aadevs.ui.rememberContentPaddingForScreen
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme
import com.amazon.appstore.aadevs.ui.video.shareVideo
import com.amazon.appstore.aadevs.ui.video.videoContentItems
import com.prof.youtubeparser.models.videos.Video

@Composable
private fun VideoTopBar(
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(Dp.Hairline, MaterialTheme.colors.onSurface.copy(alpha = .6f)),
        modifier = modifier.padding(end = 16.dp)
    ) {
        Row(Modifier.padding(horizontal = 8.dp)) {
            ShareButton(onClick = onShare)
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeVideoListWithDetail(
    uiState: HomeUiState,
    showTopAppBar: Boolean,
    onSelectVideo: (String) -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onInteractWithList: () -> Unit,
    onInteractWithDetail: (String) -> Unit,
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    homeListLazyListState: LazyListState,
    videoDetailLazyListStates: Map<String, LazyListState>,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    onSearchInputChanged: (String) -> Unit,
    onClickVideo: (String) -> Unit
) {
    HomeScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        openSearch = openSearch,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier,
    ) { hasVideosUiState, contentModifier ->
        val contentPadding = rememberContentPaddingForScreen(additionalTop = 8.dp)

        Row(contentModifier) {
            val context = LocalContext.current
            VideoList(
                videosFeed = hasVideosUiState.videosFeed,
                showExpandedSearch = !showTopAppBar,
                contentPadding = contentPadding,
                modifier = Modifier
                    .width(334.dp)
                    .notifyInput(
                        block = onInteractWithList,
                        blockRight = {
                            shareVideo(hasVideosUiState.selectedVideo, context)
                        })
                    .imePadding(),
                state = homeListLazyListState,
                searchInput = hasVideosUiState.searchInput,
                onVideoTapped = onSelectVideo,
                onSearchInputChanged = onSearchInputChanged,
            )
            Crossfade(targetState = hasVideosUiState.selectedVideo) { detailVideo ->

                val detailLazyListState by derivedStateOf {
                    videoDetailLazyListStates.getValue(detailVideo.videoId.orEmpty())
                }

                key(detailVideo.videoId) {
                    LazyColumn(
                        state = detailLazyListState,
                        contentPadding = contentPadding,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                            .notifyInput(block = { onInteractWithDetail(detailVideo.videoId.orEmpty()) },
                                blockRight = { Log.i("Right click", "Right!") }
                            )
                            .imePadding() // add padding for the on-screen keyboard
                    ) {
                        stickyHeader {
                            val currentContext = LocalContext.current
                            VideoTopBar(
                                onShare = { shareVideo(detailVideo, currentContext) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End)
                            )
                        }
                        videoContentItems(detailVideo, onClickVideo = onClickVideo)
                    }
                }
            }
        }
    }
}

@Preview("Home list detail screen", device = Devices.PIXEL_C)
@Preview("Home list detail screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_C)
@Preview("Home list detail screen (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewHomeListDetailScreen() {
    AADevsTheme {
        HomeVideoListWithDetail(
            uiState = HomeUiState.HasVideos(
                videosFeed = emptyList(),
                selectedVideo = Video(),
                isVideoOpen = false,
                isLoading = false,
                errorMessages = emptyList(),
                searchInput = ""
            ),
            showTopAppBar = true,
            onSelectVideo = {},
            onErrorDismiss = {},
            onInteractWithList = {},
            onInteractWithDetail = {},
            openDrawer = {},
            openSearch = {},
            homeListLazyListState = rememberLazyListState(),
            videoDetailLazyListStates = emptyMap(),
            scaffoldState = rememberScaffoldState(),
            onSearchInputChanged = {},
            onClickVideo = {}
        )
    }
}
