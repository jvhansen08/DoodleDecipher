package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.models.Game
import com.jaredaaronlogan.myapplication.ui.models.Player
import com.jaredaaronlogan.myapplication.ui.repositories.LobbyRepo
import com.jaredaaronlogan.myapplication.ui.repositories.UserRepository

class LobbyScreenState {
    val _players = mutableStateListOf<Player>()
    val players: List<Player> get() = _players
    var errorMessage by mutableStateOf("")
    var startGameSuccess by mutableStateOf(false)
    var gameOpen by mutableStateOf(true)
}

class LobbyViewModel(application: Application): AndroidViewModel(application) {
    val uiState = LobbyScreenState()

    fun getPlayers(joinCode: String) {
        val lobbyRef = LobbyRepo.db.getReference("lobbies").child(joinCode)
        lobbyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playersValues = snapshot.child("players").getValue<Map<String, Player>>()
                var hostExists = false
                uiState.startGameSuccess = snapshot.child("gameStarted").getValue<Boolean>() ?: false
                println(uiState.startGameSuccess)
                for (player in playersValues?.values ?: emptyList()) {
                    if (player.host) hostExists = true
                    var idExists = false
                    for (currPlayer in uiState._players) {
                        if (currPlayer.id == player.id)
                            idExists = true
                    }
                    if (!idExists) uiState._players.add(player)
                }
                if (!hostExists) uiState._players[0].host = true
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })
    }

    fun startGame(joinCode: String) {
        LobbyRepo.db.getReference("lobbies").child(joinCode).child("gameStarted").setValue(true)
        uiState.errorMessage = ""
        val game = Game(
            gameID = joinCode,
            numPlayers = uiState._players.size,
            playerMap = mapPlayers(),
            maxRounds = uiState._players.size,
            roundCounter = 0,
            drawingsMap = HashMap(),
            promptsMap = HashMap()
        )
        LobbyRepo.db.getReference("games").child(joinCode).setValue(game)
        print("Starting game...")
    }

    private fun mapPlayers(): Map<String, String> {
        val map = HashMap<String, String>()
        for (i in 0 until uiState._players.size) {
            if (i < (uiState._players.size - 1)) map[uiState._players[i].id!!] = uiState._players[i + 1].id!!
            else map[uiState._players[i].id!!] = uiState._players[0].id!!
        }
        return map
    }

    fun isHost(): Boolean {
        val uId = UserRepository.getCurrentUserId()
        for(player in uiState._players) {
            if (player.id == uId) {
                return player.host
            }
        }
        return false
    }
}