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
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.models.Drawing
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

    var userAlias by mutableStateOf("")
    var defaultUserAlias by mutableStateOf("")
    var roundCount by mutableStateOf(2)
}

class LobbyViewModel(application: Application): AndroidViewModel(application) {
    val uiState = LobbyScreenState()

    fun getPlayers(joinCode: String) {
        val lobbyRef = LobbyRepo.db.getReference("lobbies").child(joinCode)
        lobbyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val readyIndexes = ArrayList<Int>()
                val playersValues = snapshot.child("players").getValue<Map<String, Player>>()
                var hostExists = false
                uiState.startGameSuccess = snapshot.child("gameStarted").getValue<Boolean>() ?: false
                for (player in playersValues?.values ?: emptyList()) {
                    if (player.host) hostExists = true
                    if (player.id == UserRepository.getCurrentUserId()) uiState.defaultUserAlias = player.screenName
                    var currPlayerIndex = -1
                    for (i in 0 until uiState._players.size) {
                        if (uiState._players[i].ready == true) readyIndexes.plus(i)
                        if (uiState._players[i].id == player.id){
                            currPlayerIndex = i
                        }
                    }
                    if (currPlayerIndex != -1) uiState._players.removeAt(currPlayerIndex)
                    uiState._players.add(player)
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
        val drawingsMap = HashMap<String, Map<String, Drawing>>()
        val promptsMap = HashMap<String, Map<String, String>>()
        drawingsMap["0"] = HashMap()
        promptsMap["0"] = HashMap()
        val game = Game(
            gameID = joinCode,
            numPlayers = uiState._players.size,
            playerMap = mapPlayers(),
            maxRounds = uiState.roundCount - 1,
            roundCounter = 0,
            drawingsMap = drawingsMap,
            promptsMap = promptsMap,
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

    fun isReady(): Boolean {
        val uId = UserRepository.getCurrentUserId()
        for(player in uiState._players) {
            if (player.id == uId) {
                return player.ready == true
            }
        }
        return false
    }

    fun readyUp(joinCode: String) {
        val userId = UserRepository.getCurrentUserId().toString()
        LobbyRepo.db.getReference("lobbies").child(joinCode).child("players").child(userId).child("ready").setValue(true)
    }

    fun changeAlias(joinCode: String) {
        val userId = UserRepository.getCurrentUserId().toString()
        LobbyRepo.db.getReference("lobbies").child(joinCode).child("players").child(userId).child("screenName").setValue(uiState.userAlias)
    }
}