package com.amazon.appstore.aadevs.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.amazon.appstore.aadevs.R


class NotificationsHelper(val context: Context) {

    private val notificationId = 0

    private val notificationManager: NotificationManager =
        context.getSystemService() ?: throw IllegalStateException()

    fun createChannel(channelId: String, channelName: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun sendSimpleNotification(messageBody: String, applicationContext: Context) {

        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.notification_channel_id)
        )
            .setSmallIcon(R.drawable.symbol_smile_color)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(messageBody)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(messageBody)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        notificationManager.notify(notificationId, builder.build())
    }

    fun cancelNotifications() {
        notificationManager.cancelAll()
    }

    fun sendImageNotification(messageBody: String, applicationContext: Context) {
        val doggoImage = BitmapFactory.decodeResource(context.resources, R.drawable.doggo)
        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.notification_channel_id)
        )
            .setSmallIcon(R.drawable.symbol_smile_color)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(messageBody)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(doggoImage)
            )

        notificationManager.notify(notificationId, builder.build())
    }


    fun sendProgressBarNotification(applicationContext: Context) {
        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.notification_channel_id)
        )
            .setSmallIcon(R.drawable.symbol_smile_color)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.progress_notification_body))
            .setProgress(0, 0, true)

        notificationManager.notify(notificationId, builder.build())
    }
}