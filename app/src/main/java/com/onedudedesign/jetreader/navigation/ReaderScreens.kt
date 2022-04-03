package com.onedudedesign.jetreader.navigation


enum class ReaderScreens {
    ReaderSplashScreen,
    ReaderLoginScreen,
    ReaderCreateAccountScreen,
    ReaderHomeScreen,
    ReaderBookSearchScreen,
    ReaderBookDetailScreen,
    ReaderBookUpdateScreen,
    ReaderStatsScreen;

    companion object {

        fun fromRoute(route: String?): ReaderScreens
        = when (route?.substringBefore("/")) {
            ReaderSplashScreen.name -> ReaderSplashScreen
            ReaderLoginScreen.name -> ReaderLoginScreen
            ReaderCreateAccountScreen.name -> ReaderCreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            ReaderBookSearchScreen.name -> ReaderBookSearchScreen
            ReaderBookDetailScreen.name -> ReaderBookDetailScreen
            ReaderBookUpdateScreen.name -> ReaderBookUpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not registered in Enum")

        }

    }
}