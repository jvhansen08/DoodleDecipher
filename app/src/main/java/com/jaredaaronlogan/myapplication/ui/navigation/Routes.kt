package com.jaredaaronlogan.myapplication.ui.navigation

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
    val Game = Screen("game")
    val Waiting = Screen("waiting")
    val End = Screen("end")
    val Lobby = Screen("lobby")
}