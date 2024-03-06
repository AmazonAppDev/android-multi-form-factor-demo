package com.amazon.appstore.aadevs.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import com.amazon.appstore.aadevs.ui.video.VideoScreen

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    HomeRoute(
        uiState = uiState,
        isExpandedScreen = isExpandedScreen,
        onSelectVideo = { homeViewModel.selectVideo(it) },
        onErrorDismiss = { homeViewModel.errorShown(it) },
        onInteractWithList = { homeViewModel.interactedWithVideoList() },
        onInteractWithDetails = { homeViewModel.interactedWithVideoDetails(it) },
        onSearchInputChanged = { homeViewModel.onSearchInputChanged(it) },
        openDrawer = openDrawer,
        openSearch = openSearch,
        scaffoldState = scaffoldState,
        onClickVideo = { homeViewModel.onClickVideo(it) }
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    isExpandedScreen: Boolean,
    onSelectVideo: (String) -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onInteractWithList: () -> Unit,
    onInteractWithDetails: (String) -> Unit,
    onSearchInputChanged: (String) -> Unit,
    openDrawer: () -> Unit,
    openSearch: () -> Unit,
    onClickVideo: (String) -> Unit,
    scaffoldState: ScaffoldState
) {

    val homeListLazyListState = rememberLazyListState()
    val articleDetailLazyListStates = when (uiState) {
        is HomeUiState.HasVideos -> uiState.videosFeed
        is HomeUiState.NoVideos -> emptyList()
    }.associate { video ->
        key(video.videoId.orEmpty()) {
            video.videoId.orEmpty() to rememberLazyListState()
        }
    }

    when (getHomeScreenType(isExpandedScreen, uiState)) {
        HomeScreenType.VideoListWithDetail -> {
            HomeVideoListWithDetail(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onSelectVideo = onSelectVideo,
                onErrorDismiss = onErrorDismiss,
                onInteractWithList = onInteractWithList,
                onInteractWithDetail = onInteractWithDetails,
                openDrawer = openDrawer,
                openSearch = openSearch,
                homeListLazyListState = homeListLazyListState,
                videoDetailLazyListStates = articleDetailLazyListStates,
                scaffoldState = scaffoldState,
                onSearchInputChanged = onSearchInputChanged,
                onClickVideo = onClickVideo
            )
        }
        HomeScreenType.VideoList -> {
            HomeVideoList(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onErrorDismiss = onErrorDismiss,
                openDrawer = openDrawer,
                openSearch = openSearch,
                homeListLazyListState = homeListLazyListState,
                scaffoldState = scaffoldState,
                onSelectVideo = onSelectVideo,
                onSearchInputChanged = onSearchInputChanged,
            )
        }
        HomeScreenType.VideoDetail -> {
            check(uiState is HomeUiState.HasVideos)

            VideoScreen(
                video = uiState.selectedVideo,
                isExpandedScreen = isExpandedScreen,
                onBack = onInteractWithList,
                lazyListState = articleDetailLazyListStates.getValue(
                    uiState.selectedVideo.videoId.orEmpty()
                ),
                onClickVideo = onClickVideo
            )

            BackHandler {
                onInteractWithList()
            }
        }
    }
}

private enum class HomeScreenType {
    VideoListWithDetail,
    VideoList,
    VideoDetail
}

@Composable
private fun getHomeScreenType(
    isExpandedScreen: Boolean,
    uiState: HomeUiState
): HomeScreenType = when (isExpandedScreen) {
    false -> {
        when (uiState) {
            is HomeUiState.HasVideos -> {
                if (uiState.isVideoOpen) {
                    HomeScreenType.VideoDetail
                } else {
                    HomeScreenType.VideoList
                }
            }
            is HomeUiState.NoVideos -> HomeScreenType.VideoList
        }
    }
    true -> HomeScreenType.VideoListWithDetail
}
