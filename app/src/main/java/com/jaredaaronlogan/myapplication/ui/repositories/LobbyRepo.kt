package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.models.Lobby
import com.jaredaaronlogan.myapplication.ui.models.Player
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

object LobbyRepo {
    val db = Firebase.database
    suspend fun createLobby(): Lobby {

        val lobbyRef = db.getReference("lobbies")
        val joinCode = generateJoinCode()

//        val currentLobbies = lobbyRef.get().await()
//        var joinCodeInUse = currentLobbies.hasChild(joinCode)
//
//        while(joinCodeInUse) {
//            joinCode = generateJoinCode()
//            joinCodeInUse = currentLobbies.hasChild(joinCode)
//        }

        val host = Player(
            id = UserRepository.getCurrentUserId(),
            screenName = generateRandomScreenName(),
            score = 0,
            host = true,
            ready = false,
        )

        val lobby = Lobby(
            hostId = UserRepository.getCurrentUserId(),
            joinCode = joinCode,
        )

        lobbyRef.child(lobby.joinCode ?: "").setValue(lobby)

        lobbyRef.child(joinCode).child("players").child(host.id ?: "").setValue(host)

        return lobby
    }

    fun joinLobby(joinCode: String) {
        println(joinCode)

        val player = Player(
            id = UserRepository.getCurrentUserId(),
            screenName = generateRandomScreenName(),
            score = 100,
            host = false,
            ready = false,
        )

        db.getReference("lobbies").child(joinCode).child("players").child(player.id ?: "").setValue(player)
    }

    private fun updateLobby(lobby: Lobby) {
        val db = Firebase.database
        val lobbyRef = db.getReference("lobbies").child(lobby.id!!)

        lobbyRef.setValue(lobby).addOnCompleteListener {
            if (it.isSuccessful) {
                println("Lobby updated successfully") // TODO: Change to toast
            } else {
                println("Lobby update failed") // TODO: Change to toast and send back to home screen
            }
        }
    }

    private fun generateJoinCode(): String {
        val chars = ('A'..'Z') + ('0'..'9')
        val random = Random(System.currentTimeMillis())
        var code = ""
        for (i in 1..6){
            code += chars[random.nextInt(chars.size)]
        }
        return code
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