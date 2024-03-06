package com.amazon.appstore.aadevs.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme

@Composable
fun AADevsIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.symbol_smile_color),
        contentDescription = null,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun NavigationIcon(
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tintColor: Color? = null,
) {
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }

    val iconTintColor = tintColor ?: if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    Image(
        modifier = modifier,
        imageVector = icon,
        contentDescription = contentDescription,
        contentScale = ContentScale.Inside,
        colorFilter = ColorFilter.tint(iconTintColor),
        alpha = imageAlpha
    )
}

@Preview("AADevs icon")
@Preview("AADevs icon (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewIcons() {
    AADevsTheme {
        Surface {
            AADevsIcon()
        }
    }
}

@Preview("Navigation icon")
@Preview("Navigation (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNavigationIcon() {
    AADevsTheme {
        Surface {
            NavigationIcon(icon = Icons.Filled.Home, isSelected = true)
        }
    }
}
