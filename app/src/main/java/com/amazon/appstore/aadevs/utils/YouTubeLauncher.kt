package com.amazon.appstore.aadevs.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class YouTubeLauncher(val context: Context) {

    fun onClickVideo(videoId: String) {
        val videoUrl = "https://www.youtube.com/watch?v=${videoId}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent);
        }
    }
}