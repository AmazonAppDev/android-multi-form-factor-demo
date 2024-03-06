package com.amazon.appstore.aadevs.ui.featureschecker

import androidx.compose.runtime.Composable

@Composable
fun FeaturesCheckerRoute(
    featuresCheckerViewModel: FeaturesCheckerViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {
    FeaturesCheckerScreen(
        isAccelerometerSupported = { featuresCheckerViewModel.isAccelerometerSupported() },
        isRunningOnWSA = { featuresCheckerViewModel.isRunningOnWSA() },
        isHardwareKeyboardConnected = { featuresCheckerViewModel.isHardwareKeyboardConnected() },
        isExpandedScreen = isExpandedScreen,
        onBack = openDrawer
    )
}


