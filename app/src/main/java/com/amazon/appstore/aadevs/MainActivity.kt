package com.amazon.appstore.aadevs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import com.amazon.appstore.aadevs.ui.AADevsApp

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val appContainer = (application as AADevsApplication).container

        appContainer.notificationHelper.createChannel(
            this.getString(R.string.notification_channel_id),
            this.getString(R.string.notification_channel_name),
            this
        )

        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            AADevsApp(appContainer, widthSizeClass)
        }
    }
}

