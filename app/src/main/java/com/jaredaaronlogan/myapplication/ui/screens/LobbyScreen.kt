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
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.jaredaaronlogan.myapplication.ui.components.PlayerListItem
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.LobbyViewModel
import kotlinx.coroutines.launch

@Composable
fun LobbyScreen(navController: NavController, joinCode: String) {
    val viewModel: LobbyViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    if (state.startGameSuccess && state.gameOpen) {
        state.gameOpen = false
        navController.navigate(Routes.Prompt.route)
    }

    LaunchedEffect(true) {
        viewModel.getPlayers(joinCode)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = joinCode, style = MaterialTheme.typography.h3)
        }
        LazyColumn(modifier = Modifier
            .fillMaxHeight(.8f)
            .padding(16.dp)) {
            items(state.players, key = {it.id!!}) { player ->
                PlayerListItem(player = player)
            }
        }
        if (true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    scope.launch {
                        viewModel.startGame(joinCode)
//                        viewModel.getPlayers(joinCode)
                    }
                }) {
                    Text(text = "Start Game")
                }
            }
        }
    }
}