package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.components.FormField
import com.jaredaaronlogan.myapplication.ui.components.PlayerListItem
import com.jaredaaronlogan.myapplication.ui.models.Game
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.LobbyViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun LobbyScreen(navController: NavController, joinCode: String) {
    val viewModel: LobbyViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    if (state.startGameSuccess && state.gameOpen) {
        state.gameOpen = false
        navController.navigate(Routes.Prompt.route + "?gameId=${joinCode}")
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
            .fillMaxHeight(.65f)
            .padding(16.dp)) {
            items(state.players, key = {it.id!!}) { player ->
                PlayerListItem(player = player)
            }
        }
        if (viewModel.isHost()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.15f)
                .padding(5.dp)
            ) {
                Text(text = "Number of rounds: ${state.roundCount}")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
                    .padding(5.dp)
            ) {
                Slider(
                    value = state.roundCount.toFloat(),
                    onValueChange = { state.roundCount = it.roundToInt() },
                    valueRange = state.players.size.toFloat() .. 30f
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .absolutePadding(left = 15.dp, top = 5.dp, bottom = 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .fillMaxHeight()
            ) {
                FormField(
                    value = state.userAlias,
                    onValueChange = {state.userAlias = it},
                    placeholder = {Text(text = state.defaultUserAlias)}
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .absolutePadding(right = 15.dp),
                    onClick = { viewModel.changeAlias(joinCode) }
                ) {
                    Text(text = "Change name")
                }
            }
        }
        if (viewModel.isHost()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = "Player count: ${state._players.size}"
                    )
                }
                Column() {
                    if (viewModel.isReady()) {
                        if (viewModel.allPlayersAreReady()) {
                            Button(onClick = {
                                scope.launch {
                                    viewModel.startGame(joinCode)
                                }
                            }) {
                                Text(text = "Start Game")
                            }
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Waiting for players to ready up")
                            }
                        }
                    } else {
                        Button(onClick = {
                            viewModel.readyUp(joinCode)
                        }) {
                            Text(text = "Ready Up")
                        }
                    }
                }
            }
        } else{
            if (!viewModel.isReady()){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column() {
                        Button(onClick = {
                            viewModel.readyUp(joinCode)
                        }) {
                            Text(text = "Ready Up")
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column() {
                        Text(text = "Waiting for host to start game...")
                    }
                }
            }
        }
    }
}