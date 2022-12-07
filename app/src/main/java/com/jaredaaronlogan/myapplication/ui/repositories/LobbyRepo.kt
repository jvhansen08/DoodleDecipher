package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.models.Lobby
import com.jaredaaronlogan.myapplication.ui.models.Player
import kotlinx.coroutines.tasks.await

object LobbyRepo {
    suspend fun createLobby(): Lobby {
        val db = Firebase.database
        val lobbyRef = db.getReference("lobbies").push()

        val host = Player(
            id = UserRepository.getCurrentUserId(),
            screenName = generateRandomScreenName(),
            score = 0,
            host = true,
            ready = false,
        )

        val lobby = Lobby(
            id = lobbyRef.key,
            hostId = UserRepository.getCurrentUserId(),
            joinCode = generateJoinCode(),
            players = listOf(host),
        )

        lobbyRef.setValue(lobby).await()
        return lobby
    }

    private fun generateJoinCode(): String {
        val chars = ('A'..'Z') + ('0'..'9')
        return (1..4)
            .map { chars.random() }
            .joinToString("")
    }

    private fun generateRandomScreenName(): String {
        val screenNames = listOf(
            "Pixel Penguin",
            "Coding Cockatoo",
            "Enterprise Elephant",
            "Dancing Dolphin",
            "Sassy Squirrel",
            "Lucky Lizard",
            "Bouncy Beaver",
            "Olympic Ostrich",
            "Wacky Walrus",
            "Giggling Giraffe",
            "Yodeling Yak",
            "Ninja Narwhal",
            "Zany Zebra",
            "Vibrant Vulture",
            "Jolly Jaguar",
            "Kooky Koala",
        )
        return screenNames.random()
    }
}