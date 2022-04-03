package com.onedudedesign.jetreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.onedudedesign.jetreader.screens.ReaderSplashScreen
import com.onedudedesign.jetreader.screens.createaccount.ReaderCreateAccountScreen
import com.onedudedesign.jetreader.screens.details.ReaderBookDetailsScreen
import com.onedudedesign.jetreader.screens.home.ReaderHomeScreen
import com.onedudedesign.jetreader.screens.login.ReaderLoginScreen
import com.onedudedesign.jetreader.screens.search.ReaderBookSearchScreen
import com.onedudedesign.jetreader.screens.stats.ReaderStatsScreen
import com.onedudedesign.jetreader.screens.update.ReaderBookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.ReaderSplashScreen.name) {
        composable(ReaderScreens.ReaderSplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderCreateAccountScreen.name) {
            ReaderCreateAccountScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderBookDetailScreen.name) {
            ReaderBookDetailsScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderLoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderBookSearchScreen.name) {
            ReaderBookSearchScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderBookUpdateScreen.name) {
            ReaderBookUpdateScreen(navController = navController)
        }
    }
}