package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.GameViewModel

@Composable
fun GameScreen(navController: NavController, gameCode: String) {
    val viewModel: GameViewModel = viewModel()
    val state = viewModel.uiState

    if (state.round % 2 == 0){
        navController.navigate(Routes.Gallery.route)
    } else {
        navController.navigate(Routes.Studio.route)
    }
}