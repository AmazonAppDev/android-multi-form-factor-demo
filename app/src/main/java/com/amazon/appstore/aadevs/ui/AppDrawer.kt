package com.amazon.appstore.aadevs.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hardware
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.ui.component.AADevsIcon
import com.amazon.appstore.aadevs.ui.component.NavigationIcon
import com.amazon.appstore.aadevs.ui.theme.AADevsTheme

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToContactUs: () -> Unit,
    navigateToFeaturesChecker: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        AADevsLogo(Modifier.padding(16.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        DrawerButton(
            icon = Icons.Filled.ListAlt,
            label = stringResource(id = R.string.home_title),
            isSelected = currentRoute == AADevsDestinations.HOME_ROUTE,
            action = {
                navigateToHome()
                closeDrawer()
            }
        )

        DrawerButton(
            icon = Icons.Filled.Message,
            label = stringResource(id = R.string.contactus_title),
            isSelected = currentRoute == AADevsDestinations.NOTIFICATIONS_TEST_ROUTE,
            action = {
                navigateToContactUs()
                closeDrawer()
            }
        )

        DrawerButton(
            icon = Icons.Filled.Hardware,
            label = stringResource(id = R.string.features_checker_title),
            isSelected = currentRoute == AADevsDestinations.FEATURES_CHECKER_ROUTE,
            action = {
                navigateToFeaturesChecker()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun AADevsLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        AADevsIcon()
        Spacer(Modifier.width(8.dp))
        Text(stringResource(R.string.app_name))
    }
}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationIcon(
                    icon = icon,
                    isSelected = isSelected,
                    contentDescription = null, // decorative
                    tintColor = textIconColor
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    AADevsTheme {
        Surface {
            AppDrawer(
                currentRoute = AADevsDestinations.HOME_ROUTE,
                navigateToHome = {},
                navigateToContactUs = {},
                navigateToFeaturesChecker = {},
                closeDrawer = { }
            )
        }
    }
}
