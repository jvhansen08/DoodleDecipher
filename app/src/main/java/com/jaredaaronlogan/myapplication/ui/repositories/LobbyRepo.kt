package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.components.Lobby
import com.jaredaaronlogan.myapplication.ui.components.Player
import kotlinx.coroutines.tasks.await

object LobbyRepo {

    suspend fun createLobby(): Lobby {
        val doc = Firebase.firestore.collection("lobbies").document()
        val lobby = Lobby(
            id = doc.id,
            joinCode = generateGameCode(),
            host = UserRepository.getCurrentUserId()!!,
        )
        doc.set(lobby).await()
        return lobby
    }

    suspend fun addPlayer(player: Player, joinCode: String) {
        val lobby = getLobbyByJoinCode(joinCode)
        lobby.addPlayer(player)
        updateLobby(lobby)
    }

    suspend fun removePlayer(player: Player, joinCode: String) {
        val lobby = getLobbyByJoinCode(joinCode)
        lobby.removePlayer(player)
        updateLobby(lobby)
    }

    private fun generateGameCode(): String {
        val chars = ('A'..'Z') + ('0'..'9')
        return (1..4)
            .map { chars.random() }
            .joinToString("")
    }

    private suspend fun getLobbyByJoinCode(joinCode: String): Lobby {
        val snapshot = Firebase.firestore
            .collection("lobbies")
            .whereEqualTo("joinCode", joinCode)
            .get()
            .await()
        return snapshot.toObjects<Lobby>().first()
    }

    private suspend fun updateLobby(lobby: Lobby) {
        Firebase.firestore
            .collection("lobbies")
            .document(lobby.id)
            .set(lobby)
            .await()
    }

    suspend fun getPlayers(joinCode: String): MutableList<Player> {
        val lobby = getLobbyByJoinCode(joinCode)
        return lobby.players
    }
}