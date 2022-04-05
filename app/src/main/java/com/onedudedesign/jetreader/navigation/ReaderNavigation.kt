package com.onedudedesign.jetreader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onedudedesign.jetreader.screens.ReaderSplashScreen
import com.onedudedesign.jetreader.screens.createaccount.ReaderCreateAccountScreen
import com.onedudedesign.jetreader.screens.details.ReaderBookDetailsScreen
import com.onedudedesign.jetreader.screens.home.ReaderHomeScreen
import com.onedudedesign.jetreader.screens.login.ReaderLoginScreen
import com.onedudedesign.jetreader.screens.search.ReaderBookSearchScreen
import com.onedudedesign.jetreader.screens.search.ReaderBookSearchViewModel
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
        val detailName = ReaderScreens.ReaderBookDetailScreen.name
        composable("$detailName/{bookId}",
            arguments = listOf(
                navArgument(name = "bookId") {
                    type = NavType.StringType
                }
            )) {navBack ->
            navBack.arguments?.getString("bookId").let { bookId ->
                ReaderBookDetailsScreen(navController = navController, bookId = bookId.toString())
            }

        }
//        composable(ReaderScreens.ReaderBookDetailScreen.name) {
//            ReaderBookDetailsScreen(navController = navController, bookId = "")
//        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderLoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderBookSearchScreen.name) {
            val searchViewModel = hiltViewModel<ReaderBookSearchViewModel>()
            ReaderBookSearchScreen(navController = navController, viewModel = searchViewModel)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderBookUpdateScreen.name) {
            ReaderBookUpdateScreen(navController = navController)
        }
    }
}