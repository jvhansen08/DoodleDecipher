package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.GameViewModel

@Composable
fun GameScreen(navController: NavController, gameId: String) {
    val viewModel: GameViewModel = viewModel()
    val state = viewModel.uiState
    viewModel.initialize(gameId)
    println("Welcome to the game screen")

    if (state.round % 2 == 0){
        Button(onClick = { navController.navigate(Routes.Gallery.route + "?gameId=$gameId") }) {
            Text(text = "Let me guess...")
        }

    } else {
        Button(onClick = { navController.navigate(Routes.Studio.route + "?gameId=$gameId") }) {
            Text(text = "To the drawing board!")
        }
    }
}