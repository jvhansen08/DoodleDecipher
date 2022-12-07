package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.components.PlayerListItem
import com.jaredaaronlogan.myapplication.ui.viewmodels.LobbyViewModel
import kotlinx.coroutines.launch

@Composable
fun LobbyScreen(navController: NavController) {
    val viewModel: LobbyViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.players.isEmpty()) {
        viewModel.setupInitialState()
    }

    Column(
        modifier = Modifier
            .background(color = Color(0xFFf8EDEB))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text ="Lobby Screen", style = MaterialTheme.typography.h2)
        }
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)) {
            items(state.players, key = {it.id!!}) { player ->
                PlayerListItem(player = player)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                scope.launch {
                    viewModel.startGame()
                }
            }) {
                Text(text = "Start Game")
            }
        }
    }
}