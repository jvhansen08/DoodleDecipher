package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.jaredaaronlogan.myapplication.ui.models.Lobby
import com.jaredaaronlogan.myapplication.ui.models.Player
import com.jaredaaronlogan.myapplication.ui.repositories.LobbyRepo

class LobbyScreenState {
    val _players = mutableStateListOf<Player>()
    val players: List<Player> get() = _players
    var lobby by mutableStateOf<Lobby?>(null)
    var errorMessage by mutableStateOf("")
    var startGameSuccess by mutableStateOf(false)
}

class LobbyViewModel(application: Application): AndroidViewModel(application) {
    val uiState = LobbyScreenState()

    fun setupInitialState() {
        uiState.lobby = LobbyRepo.createLobby()
    }

    fun joinLobby(joinCode: String) {
        uiState.errorMessage = ""
        val lobby = LobbyRepo.getLobby(joinCode)
        if (lobby == null) {
            uiState.errorMessage = "Lobby not found."
        } else {
            uiState.lobby = lobby
        }
    }

    fun startGame() {
        uiState.errorMessage = ""
        uiState.startGameSuccess = true
        print("Starting game...")
        // print list of current players in lobby when game is starting
        print(uiState.lobby?.players)
    }
}