package com.onedudedesign.jetreader.screens.stats

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.onedudedesign.jetreader.components.ReaderLogo

@Composable
fun ReaderStatsScreen(navController: NavHostController) {
    ReaderLogo()
    Text(text = "Stats Screen")

}