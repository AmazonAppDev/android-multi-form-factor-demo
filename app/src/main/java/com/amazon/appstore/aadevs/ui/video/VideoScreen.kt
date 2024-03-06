package com.amazon.appstore.aadevs.ui.video

import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.ui.ShareButton
import com.amazon.appstore.aadevs.ui.modifiers.interceptKey
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme
import com.amazon.appstore.aadevs.utils.isScrolled
import com.prof.youtubeparser.models.videos.Video
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VideoScreen(
    video: Video,
    isExpandedScreen: Boolean,
    onBack: () -> Unit,
    onClickVideo: (String) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val context = LocalContext.current

    Row(modifier.fillMaxSize()) {
        val context = LocalContext.current
        VideoScreenContent(
            video = video,
            navigationIconContent = if (!isExpandedScreen) {
                {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_up),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            } else {
                null
            },
            bottomBarContent = if (!isExpandedScreen) {
                {
                    BottomBar(
                        onShareVideo = { shareVideo(video, context) },
                    )
                }
            } else {
                { }
            },
            lazyListState = lazyListState,
            onClickVideo = onClickVideo
        )
    }
}

@Composable
private fun VideoScreenContent(
    video: Video,
    navigationIconContent: @Composable (() -> Unit)? = null,
    bottomBarContent: @Composable () -> Unit = { },
    onClickVideo: (String) -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = Html.fromHtml(video.title.orEmpty(), Html.FROM_HTML_MODE_LEGACY)
                            .toString(),
                        style = MaterialTheme.typography.h6,
                        color = LocalContentColor.current,
                        modifier = Modifier.padding(start = 10.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = navigationIconContent,
                elevation = if (!lazyListState.isScrolled) 0.dp else 4.dp,
                backgroundColor = MaterialTheme.colors.surface
            )
        },
        bottomBar = bottomBarContent
    ) { innerPadding ->
        VideoContent(
            video = video,
            modifier = Modifier.padding(innerPadding),
            state = lazyListState,
            onClickVideo = onClickVideo,
        )
    }
}

@Composable
private fun BottomBar(
    onShareVideo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(elevation = 8.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Vertical))
                .height(56.dp)
                .fillMaxWidth()
        ) {
            ShareButton(onClick = onShareVideo)
        }
    }
}

fun shareVideo(video: Video, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, video.videoId)
        putExtra(Intent.EXTRA_TEXT, video.title)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.video_share)
        )
    )
}

@Preview("Video screen")
@Preview("Video screen (dark)", uiMode = UI_MODE_NIGHT_YES)
@Preview("Video screen (big font)", fontScale = 1.5f)
@Composable
fun PreviewVideoScreenDrawer() {
    AADevsTheme {
        val video = runBlocking {
            Video("aVideo", "aaa", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.com%2FAmazon-Developers-Appstore%2Fdp%2FB076QVLWQ1&psig=AOvVaw1qP94NR1Yxm6W4UTH4avs4&ust=1664899029450000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCJCDzqy2xPoCFQAAAAAdAAAAABAD", "10/3/2022")
        }
        VideoScreen(video, false, {}, {})
    }
}

@Preview("Video screen navrail", device = Devices.PIXEL_C)
@Preview(
    "Video screen navrail (dark)",
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Preview("Video screen navrail (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewVideoScreenNavRail() {
    AADevsTheme {
        val video = runBlocking {
            Video("aVideo", "aaa", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.com%2FAmazon-Developers-Appstore%2Fdp%2FB076QVLWQ1&psig=AOvVaw1qP94NR1Yxm6W4UTH4avs4&ust=1664899029450000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCJCDzqy2xPoCFQAAAAAdAAAAABAD", "10/3/2022")
        }
        VideoScreen(video, true, {}, {})
    }
}
