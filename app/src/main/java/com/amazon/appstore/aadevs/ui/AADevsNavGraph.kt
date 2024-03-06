package com.amazon.appstore.aadevs.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amazon.appstore.aadevs.AppContainer
import com.amazon.appstore.aadevs.ui.featureschecker.FeaturesCheckerRoute
import com.amazon.appstore.aadevs.ui.featureschecker.FeaturesCheckerViewModel
import com.amazon.appstore.aadevs.ui.notifications.NotificationsTestRoute
import com.amazon.appstore.aadevs.ui.notifications.NotificationsTestViewModel
import com.amazon.appstore.aadevs.ui.home.HomeRoute
import com.amazon.appstore.aadevs.ui.home.HomeViewModel

@Composable
fun AADevsNavGraph(
    appContainer: AppContainer,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    openSearch: () -> Unit = {},
    startDestination: String = AADevsDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AADevsDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(appContainer.videosRepository, appContainer.youTubeLauncher)
            )
            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                openSearch = openSearch
            )
        }
        composable(AADevsDestinations.NOTIFICATIONS_TEST_ROUTE) {
            val notificationsTestViewModel: NotificationsTestViewModel = viewModel(
                factory = NotificationsTestViewModel.provideFactory(appContainer.notificationHelper)
            )
            NotificationsTestRoute(
                notificationsTestViewModel = notificationsTestViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(AADevsDestinations.FEATURES_CHECKER_ROUTE) {
            val featuresCheckerViewModel: FeaturesCheckerViewModel = viewModel(
                factory = FeaturesCheckerViewModel.provideFactory(appContainer.featuresChecker)
            )
            FeaturesCheckerRoute(
                featuresCheckerViewModel = featuresCheckerViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
    }
}
