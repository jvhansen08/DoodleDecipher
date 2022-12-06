package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jaredaaronlogan.myapplication.ui.components.Player
import com.jaredaaronlogan.myapplication.ui.repositories.LobbyRepo

class LobbyScreenState {
    val _players = mutableListOf<Player>()
    val players: List<Player> get() = _players
}

class LobbyViewModel(application: Application): AndroidViewModel(application) {
    val uiState = LobbyScreenState()
    suspend fun getPlayers(joinCode: String) {
        val players = LobbyRepo.getPlayers(joinCode)
        uiState._players.clear()
        uiState._players.addAll(players)
    }

    suspend fun addPlayer(player: Player, joinCode: String) {
        LobbyRepo.addPlayer(player, joinCode)
    }
}