package com.jaredaaronlogan.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaredaaronlogan.myapplication.ui.screens.GalleryScreen
import com.jaredaaronlogan.myapplication.ui.screens.HomeScreen
import com.jaredaaronlogan.myapplication.ui.screens.StudioScreen

@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(Routes.Home.route) { HomeScreen(navController = navController) }
        composable(Routes.Gallery.route) { GalleryScreen(navController = navController)}
        composable(Routes.Studio.route) { StudioScreen(navController = navController) }
    }
}