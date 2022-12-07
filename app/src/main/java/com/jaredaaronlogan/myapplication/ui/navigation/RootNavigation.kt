package com.jaredaaronlogan.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jaredaaronlogan.myapplication.ui.screens.*

@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        navigation(route = Routes.LaunchNavigation.route, startDestination = Routes.Launch.route) {
            composable(route = Routes.Launch.route) { LaunchScreen(navController) }
            composable(route = Routes.SignIn.route) { SignInScreen(navController) }
            composable(route = Routes.SignUp.route) { SignUpScreen(navController) }
        }
        navigation(route = Routes.GameNavigation.route, startDestination = Routes.Home.route) {
            composable(Routes.Home.route) { HomeScreen(navController = navController) }
            composable(Routes.Gallery.route) { GalleryScreen(navController = navController)}
            composable(Routes.Studio.route) { StudioScreen(navController = navController) }
            composable(Routes.Lobby.route) { LobbyScreen(navController = navController) }
        }
        composable(route = Routes.Splash.route) { SplashScreen(navController) }
    }
}