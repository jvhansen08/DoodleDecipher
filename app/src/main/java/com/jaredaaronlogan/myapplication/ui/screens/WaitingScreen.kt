package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.WaitingViewModel
import com.usu.firebasetodosapplication.ui.components.Loader

@Composable
fun WaitingScreen(navController: NavController, gameId: String) {
    val viewModel: WaitingViewModel = viewModel()
    val state = viewModel.uiState
    viewModel.initialize(gameId)
    if (state.gameOver) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(.5f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Game Over")

            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { navController.navigate(Routes.End.route + "?gameId=$gameId")}) {
                    Text(text = "View Results")
                }
            }
        }
    } else {
        if (!state.waiting) {
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFf8EDEB))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { navController.navigate(Routes.Game.route + "?gameId=$gameId") }) {
                        Text(text = "Next Round")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFf8EDEB))
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Loader()
                }
            }
        }
    }

}