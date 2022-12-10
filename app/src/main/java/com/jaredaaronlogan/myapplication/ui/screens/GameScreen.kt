package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Column(
            modifier = Modifier
                .background(color = Color(0xFFf8EDEB))
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Your Prompt:", style = MaterialTheme.typography.h4)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = state.prompt, style = MaterialTheme.typography.h6)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Button(onClick = { navController.navigate(Routes.Studio.route + "?gameId=$gameId") }) {
                    Text(text = "To the drawing board!")
                }
            }
        }
    }
}