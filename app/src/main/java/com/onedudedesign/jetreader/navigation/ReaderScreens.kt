package com.onedudedesign.jetreader.navigation


enum class ReaderScreens {
    ReaderSplashScreen,
    ReaderLoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    ReaderBookDetailScreen,
    ReaderBookUpdateScreen,
    ReaderStatsScreen;

    companion object {

        fun fromRoute(route: String?): ReaderScreens
        = when (route?.substringBefore("/")) {
            ReaderSplashScreen.name -> ReaderSplashScreen
            ReaderLoginScreen.name -> ReaderLoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            ReaderBookDetailScreen.name -> ReaderBookDetailScreen
            ReaderBookUpdateScreen.name -> ReaderBookUpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not registered in Enum")

        }

    }
}