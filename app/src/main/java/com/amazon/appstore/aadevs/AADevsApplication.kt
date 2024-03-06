package com.amazon.appstore.aadevs

import android.app.Application

class AADevsApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}
