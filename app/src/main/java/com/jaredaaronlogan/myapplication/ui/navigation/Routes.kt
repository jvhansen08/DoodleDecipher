package com.jaredaaronlogan.myapplication.ui.navigation

data class Screen(val route: String)

object Routes {
    val Home = Screen("home")
    val Gallery = Screen("gallery")
    val Studio = Screen("studio")
}