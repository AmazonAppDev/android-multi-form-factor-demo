package com.amazon.appstore.aadevs.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object AADevsDestinations {
    const val HOME_ROUTE = "home"
    const val NOTIFICATIONS_TEST_ROUTE = "notificationstest"
    const val FEATURES_CHECKER_ROUTE = "featureschecker"
}

class AADevsNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(AADevsDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToContactUs: () -> Unit = {
        navController.navigate(AADevsDestinations.NOTIFICATIONS_TEST_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToFeaturesChecker: () -> Unit = {
        navController.navigate(AADevsDestinations.FEATURES_CHECKER_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
