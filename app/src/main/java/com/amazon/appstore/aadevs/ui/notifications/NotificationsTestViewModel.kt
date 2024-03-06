package com.amazon.appstore.aadevs.ui.notifications

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.utils.NotificationsHelper

class NotificationsTestViewModel(private val notificationsHelper: NotificationsHelper) : ViewModel() {

    fun launchSimpleNotification(context: Context) {
        notificationsHelper.sendSimpleNotification(
            context.getText(R.string.app_name).toString(),
            context
        )
    }

    fun launchImageNotification(context: Context) {
        notificationsHelper.sendImageNotification(
            context.getText(R.string.app_name).toString(),
            context
        )
    }

    fun launchProgressBarNotification(context: Context) {
        notificationsHelper.sendProgressBarNotification(
            context
        )
    }

    companion object {
        fun provideFactory(notificationsHelper: NotificationsHelper
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotificationsTestViewModel(notificationsHelper) as T
            }
        }
    }
}
