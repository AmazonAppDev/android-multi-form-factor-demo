package com.amazon.appstore.aadevs.ui.notifications

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun NotificationsTestRoute(
    notificationsTestViewModel: NotificationsTestViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {
    val localContext: Context = LocalContext.current

    NotificationsTestScreen(
        launchSimpleNotification = { notificationsTestViewModel.launchSimpleNotification(localContext) },
        launchImageNotification = { notificationsTestViewModel.launchImageNotification(localContext) },
        launchProgressBarNotification = { notificationsTestViewModel.launchProgressBarNotification(localContext) },
        isExpandedScreen = isExpandedScreen,
        onBack = openDrawer
    )
}


