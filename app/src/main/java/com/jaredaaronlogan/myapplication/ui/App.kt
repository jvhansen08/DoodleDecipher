package com.jaredaaronlogan.myapplication.ui

import androidx.compose.runtime.Composable
import com.jaredaaronlogan.myapplication.ui.navigation.RootNavigation
import com.jaredaaronlogan.myapplication.ui.theme.DoodleDecipherTheme

@Composable
fun App() {
    DoodleDecipherTheme {
        RootNavigation()
    }
}