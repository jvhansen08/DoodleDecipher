package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.WaitingViewModel

@Composable
fun WaitingScreen(navController: NavController, gameId: String) {
    val viewModel: WaitingViewModel = viewModel()
    val state = viewModel.uiState

    viewModel.initialize(gameId)

    if (!state.waiting) {
        navController.navigate(Routes.Waiting.route)
    }
}