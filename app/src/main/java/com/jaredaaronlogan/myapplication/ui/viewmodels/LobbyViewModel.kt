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
//        uiState.gameOpen = false
//        uiState.startGameSuccess = true
        print("Starting game...")
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