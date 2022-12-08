package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.models.Lobby
import com.jaredaaronlogan.myapplication.ui.models.Player
import kotlin.random.Random

object LobbyRepo {
    private val db = Firebase.database
    fun createLobby(): String {

        val lobbyRef = db.getReference("lobbies")
        val joinCode = generateJoinCode()

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

        readData(joinCode)

        return joinCode
    }

    fun joinLobby(joinCode: String) {
        val player = Player(
            id = UserRepository.getCurrentUserId(),
            screenName = generateRandomScreenName(),
            score = 0,
            host = false,
            ready = false,
        )

        db.getReference("lobbies").child(joinCode).child("players").child(player.id ?: "").setValue(player)
        readData(joinCode)
    }

    private fun readData(joinCode: String) {
        val lobbyRef = db.getReference("lobbies").child(joinCode)
        lobbyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lobby = snapshot.getValue<Lobby>()
                println(lobby)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })
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