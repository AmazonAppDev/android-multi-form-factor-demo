package com.amazon.appstore.aadevs.ui.video

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme
import com.amazon.appstore.aadevs.ui.theme.AmazonOrange
import com.prof.youtubeparser.models.videos.Video
import kotlinx.coroutines.runBlocking

private val defaultSpacerSize = 16.dp

@Composable
fun VideoContent(
    video: Video,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onClickVideo: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = defaultSpacerSize),
        state = state,
    ) {
        videoContentItems(video, onClickVideo = onClickVideo)
    }
}

fun LazyListScope.videoContentItems(video: Video, onClickVideo: (String) -> Unit) {
    item {
        Spacer(Modifier.height(defaultSpacerSize))
        VideoHeaderImage(video, onClickVideo = onClickVideo)
    }
    item {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val borderColor = if (isHovered) AmazonOrange else Color.White

        Text(
            text = Html.fromHtml(video.title.orEmpty(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString(), style = MaterialTheme.typography.h4,
            modifier = Modifier
                .hoverable(interactionSource = interactionSource)
                .background(borderColor, RectangleShape)
                .padding(10.dp)
        )
        Spacer(Modifier.height(8.dp))
    }

    item {
        Spacer(Modifier.height(48.dp))
    }

    item {
        Text(text = LoremIpsum().values.joinToString(), style = MaterialTheme.typography.body1)
        Spacer(Modifier.height(8.dp))
    }

    item {
        Spacer(Modifier.height(48.dp))
    }
}

@Composable
private fun VideoHeaderImage(
    video: Video,
    onClickVideo: (String) -> Unit
) {

    val imageModifier = Modifier
        .heightIn(min = 150.dp, max = 500.dp)
        .fillMaxWidth()
        .clickable { onClickVideo(video.videoId.orEmpty()) }
        .clip(shape = MaterialTheme.shapes.medium)

    if (video.coverLink != null) {
        AsyncImage(
            model = video.coverLink,
            contentDescription = null,
            placeholder = painterResource(R.drawable.placeholder_4_3),
            modifier = imageModifier,
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(defaultSpacerSize))
    } else {
        Image(
            painter = painterResource(R.drawable.placeholder_4_3),
            contentDescription = null, // decorative
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    }
}


@Preview("Video content")
@Preview("Video content (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewVideoContent() {
    AADevsTheme {
        Surface {
            val video = runBlocking {
                Video("aVideo", "aaa", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.amazon.com%2FAmazon-Developers-Appstore%2Fdp%2FB076QVLWQ1&psig=AOvVaw1qP94NR1Yxm6W4UTH4avs4&ust=1664899029450000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCJCDzqy2xPoCFQAAAAAdAAAAABAD", "10/3/2022")
            }
            VideoContent(video = video)
        }
    }
}
