package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.WaitingViewModel

@Composable
fun WaitingScreen(navController: NavController, gameId: String) {
    val viewModel: WaitingViewModel = viewModel()
    val state = viewModel.uiState
    println("Welcome to the waiting screen")
    viewModel.initialize(gameId)


    if (!state.waiting) {
        Button(onClick = { navController.navigate(Routes.Game.route + "?gameId=$gameId") }) {
            Text(text = "Next Round")
        }
        println(gameId)
    }
}