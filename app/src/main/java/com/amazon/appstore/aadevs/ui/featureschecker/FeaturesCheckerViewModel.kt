package com.amazon.appstore.aadevs.ui.featureschecker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amazon.appstore.aadevs.utils.FeaturesChecker

class FeaturesCheckerViewModel(private val featuresChecker: FeaturesChecker) : ViewModel() {

    fun isAccelerometerSupported() = featuresChecker.isAccelerometerSupported()

    fun isRunningOnWSA() = featuresChecker.isRunningOnWSA()

    fun isHardwareKeyboardConnected() = featuresChecker.isHardwareKeyboardConnected()

    companion object {
        fun provideFactory(featuresChecker: FeaturesChecker
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FeaturesCheckerViewModel(featuresChecker) as T
            }
        }
    }
}
