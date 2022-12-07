package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.models.Lobby
import com.jaredaaronlogan.myapplication.ui.models.Player

object LobbyRepo {
    fun createLobby(): Lobby {
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

        lobbyRef.setValue(lobby).addOnCompleteListener {
            if (it.isSuccessful) {
                println("Lobby created successfully") // TODO: Change to toast
            } else {
                println("Lobby creation failed") // TODO: Change to toast and send back to home screen
            }
        }

        lobbyRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newLobby = snapshot.getValue<Lobby>()
                println("New Lobby: $newLobby")
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })

        return lobby
    }

    fun joinLobby(joinCode: String): Lobby {
        val db = Firebase.database
        val lobbyRef = db.getReference("lobbies").orderByChild("joinCode").equalTo(joinCode).limitToFirst(1)

        var lobby: Lobby? = null

        lobbyRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    lobby = child.getValue<Lobby>()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })

        if (lobby == null) {
            println("Lobby not found") // TODO: Change to toast
        } else {
            val player = Player(
                id = UserRepository.getCurrentUserId(),
                screenName = generateRandomScreenName(),
                score = 0,
                host = false,
                ready = false,
            )

            lobby!!.players = lobby!!.players?.plus(player)
            updateLobby(lobby!!)
        }

        return lobby!!
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

    fun getLobby(joinCode: String): Lobby? {
        val db = Firebase.database
        val lobbyRef = db.getReference("lobbies").orderByChild("joinCode").equalTo(joinCode).limitToFirst(1)

        var lobby: Lobby? = null

        lobbyRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    lobby = child.getValue<Lobby>()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })

        return lobby
    }
}