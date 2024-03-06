package com.amazon.appstore.aadevs.ui.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hardware
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.ui.AADevsDestinations
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme
import com.amazon.appstore.aadevs.utils.FeaturesChecker

@Composable
fun AADevsNavRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRail(
        modifier = modifier,
        elevation = 0.dp,
        header = header
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

@Composable
fun AppNavRail(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToContactUs: () -> Unit,
    navigateToFeaturesChecker: () -> Unit,
    modifier: Modifier = Modifier
) {
    AADevsNavRail(
        header = {
            AADevsIcon(Modifier.padding(top = 8.dp))
        },
        modifier = modifier
    ) {
        NavRailIcon(
            icon = Icons.Filled.Home,
            contentDescription = stringResource(id = R.string.cd_navigate_home),
            isSelected = currentRoute == AADevsDestinations.HOME_ROUTE,
            action = navigateToHome
        )
        Spacer(modifier = Modifier.height(16.dp))
        NavRailIcon(
            icon = Icons.Filled.ListAlt,
            contentDescription = stringResource(id = R.string.cd_navigate_notifications),
            isSelected = currentRoute == AADevsDestinations.NOTIFICATIONS_TEST_ROUTE,
            action = navigateToContactUs
        )
        Spacer(modifier = Modifier.height(16.dp))
        NavRailIcon(
            icon = Icons.Filled.Hardware,
            contentDescription = stringResource(id = R.string.cd_navigate_features_checker),
            isSelected = currentRoute == AADevsDestinations.FEATURES_CHECKER_ROUTE,
            action = navigateToFeaturesChecker
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NavRailIcon(
    icon: ImageVector,
    contentDescription: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        if (isSelected) {
            MaterialTheme.colors.primary.copy(alpha = 0.12f)
        } else {
            Color.Transparent
        }
    )

    Surface(
        selected = isSelected,
        color = backgroundColor,
        onClick = action,
        shape = CircleShape,
        modifier = modifier.size(48.dp)
    ) {
        NavigationIcon(
            icon = icon,
            isSelected = isSelected,
            contentDescription = contentDescription,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppNavRail() {
    AADevsTheme {
        AppNavRail(
            currentRoute = AADevsDestinations.HOME_ROUTE,
            navigateToHome = {},
            navigateToContactUs = {},
            navigateToFeaturesChecker = {}
        )
    }
}
