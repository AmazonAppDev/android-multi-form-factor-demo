package com.amazon.appstore.aadevs.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build


class FeaturesChecker(val context: Context) {

    fun isRunningOnWSA() = ("Subsystem for Android(TM)" == Build.MODEL)


    fun isAccelerometerSupported(): Boolean {
        val packageManager: PackageManager = context.packageManager
        return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)
    }

    fun isHardwareKeyboardConnected() =
        context.resources.configuration.keyboard == Configuration.KEYBOARD_QWERTY

}