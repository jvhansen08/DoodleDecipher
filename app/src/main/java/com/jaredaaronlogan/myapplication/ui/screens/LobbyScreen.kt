package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.components.PlayerListItem
import com.jaredaaronlogan.myapplication.ui.viewmodels.LobbyViewModel

@Composable
fun LobbyScreen(navController: NavController) {
    val viewModel: LobbyViewModel = viewModel()
    val state = viewModel.uiState

    LaunchedEffect(true) {
        viewModel.getPlayers("GAME") // figure out how to handle game id
    }

    Column() {
        Row() {
            Text(text = "Lobby Code: GAME")
        }
        Row() {
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)) {
                items(state.players, key = {it.id!!}) { player ->
                    PlayerListItem(
                        player = player,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}