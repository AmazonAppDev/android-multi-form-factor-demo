package com.amazon.appstore.aadevs.ui.home

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme
import com.prof.youtubeparser.models.videos.Video

@Composable
fun VideoImage(
    video: Video,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = video.coverLink,
        contentDescription = null,
        placeholder = painterResource(R.drawable.placeholder_4_3),
        modifier = modifier
            .size(80.dp, 80.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun VideoTitle(video: Video) {
    Text(
        Html.fromHtml(video.title.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(end = 16.dp)
    )
}

@Composable
fun VideoItem(
    video: Video,
    navigateToDetail: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = { navigateToDetail(video.videoId.orEmpty()) })
    ) {
        VideoImage(
            video = video,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        VideoTitle(video = video)
    }
}

@Preview("Videos Item")
@Composable
fun VideosItemPreview() {
    AADevsTheme {
        Surface {
            VideoItem(Video("aTitle",
            "hLbkv_ECBJk",
            "https://i.ytimg.com/vi/hLbkv_ECBJk/hqdefault_16166.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLAo24iPSfqCT6_CBknLdkLjQNeesQ")) {}
        }
    }
}
