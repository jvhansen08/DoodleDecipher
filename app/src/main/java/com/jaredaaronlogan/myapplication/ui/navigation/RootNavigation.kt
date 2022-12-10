package com.jaredaaronlogan.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            composable(
                route = Routes.Prompt.route + "?gameId={gameId}",
                arguments = listOf(navArgument("gameId") { defaultValue = "000000" })
            ) {
                navBackStackEntry ->
                PromptScreen(navController = navController, gameId = navBackStackEntry.arguments?.getString("gameId") ?: "000000")
            }
            composable(
                route = Routes.Lobby.route + "?joinCode={joinCode}",
                arguments = listOf(navArgument("joinCode") { defaultValue = "000000" })
            ) { navBackStackEntry ->
                LobbyScreen(navController = navController, navBackStackEntry.arguments?.getString("joinCode") ?: "")
            }
            composable(
                route = Routes.Gallery.route + "?gameCode={gameCode}",
                arguments = listOf(navArgument("gameCode") { defaultValue = "000000" })
            ) { navBackStackEntry ->
                GalleryScreen(navController = navController, navBackStackEntry.arguments?.getString("gameCode") ?: "")
            }
            composable(
                route = Routes.Studio.route + "?gameCode={gameCode}",
                arguments = listOf(navArgument("gameCode") { defaultValue = "000000" })
            ) { navBackStackEntry ->
                StudioScreen(navController = navController, navBackStackEntry.arguments?.getString("gameCode") ?: "")
            }
            composable(
                route = Routes.Game.route + "?gameCode={gameCode}",
                arguments = listOf(navArgument("gameCode") { defaultValue = "000000" })
            ) { navBackStackEntry ->
                GameScreen(navController = navController, navBackStackEntry.arguments?.getString("gameCode") ?: "")
            }
            composable(
                route = Routes.Waiting.route + "?gameCode={gameCode}",
                arguments = listOf(navArgument("gameCode") { defaultValue = "000000" })
            ) { navBackStackEntry ->
                WaitingScreen(navController = navController, navBackStackEntry.arguments?.getString("gameCode") ?: "")
            }
        }
        composable(route = Routes.Splash.route) { SplashScreen(navController) }
    }
}