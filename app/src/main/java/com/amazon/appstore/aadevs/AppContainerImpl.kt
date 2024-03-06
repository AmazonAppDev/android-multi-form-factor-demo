package com.amazon.appstore.aadevs

import android.content.Context
import com.amazon.appstore.aadevs.data.videos.VideosRepository
import com.amazon.appstore.aadevs.data.videos.YoutubeVideosRepository
import com.amazon.appstore.aadevs.utils.FeaturesChecker
import com.amazon.appstore.aadevs.utils.NotificationsHelper
import com.amazon.appstore.aadevs.utils.YouTubeLauncher

interface AppContainer {
    val videosRepository: VideosRepository
    val notificationHelper: NotificationsHelper
    val youTubeLauncher: YouTubeLauncher
    val featuresChecker: FeaturesChecker
}

class AppContainerImpl(private val context: Context) : AppContainer {

    override val videosRepository: VideosRepository by lazy {
        YoutubeVideosRepository()
    }

    override val notificationHelper: NotificationsHelper by lazy {
        NotificationsHelper(context)
    }

    override val youTubeLauncher: YouTubeLauncher by lazy {
        YouTubeLauncher(context)
    }

    override val featuresChecker: FeaturesChecker by lazy {
        FeaturesChecker(context)
    }
}