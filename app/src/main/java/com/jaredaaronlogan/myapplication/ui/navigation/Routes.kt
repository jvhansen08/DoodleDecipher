package com.jaredaaronlogan.myapplication.ui.navigation

import android.window.SplashScreen

data class Screen(val route: String)

object Routes {
    val Home = Screen("home")
    val Gallery = Screen("gallery")
    val Studio = Screen("studio")
    val LaunchNavigation = Screen("launchnavigation")
    val GameNavigation = Screen("gamenavigation")
    val Launch = Screen("launch")
    val SignIn = Screen("signin")
    val SignUp = Screen("signup")
    val Splash = Screen("splash")
    val Prompt = Screen("prompt")
    val Lobby = Screen("lobby")
}