package com.jaredaaronlogan.myapplication.ui.repositories

import android.graphics.Path
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.Color
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.components.Segment
import com.jaredaaronlogan.myapplication.ui.models.Lobby
import com.jaredaaronlogan.myapplication.ui.models.Player
import kotlin.random.Random

object LobbyRepo {
    val db = Firebase.database
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

    fun saveImage(drawing: Drawing) {
        val drawingRef = db.getReference("drawings")
        drawingRef.child("drawing1").setValue(drawing)
    }

    private fun readData(joinCode: String) {
        val lobbyRef = db.getReference("lobbies").child(joinCode)
        lobbyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playersValues = snapshot.child("players").getValue<Map<String, Player>>()
                for (player in playersValues?.values ?: emptyList()) {
                    println(player.screenName)
                }
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
        val random = Random(System.currentTimeMillis())
        return screenNames[random.nextInt(screenNames.size)]
    }
}
//val drawing: Drawing
//val segments = ArrayList<Segment>()
//val sketchRef = LobbyRepo.db.getReference("drawings").child(drawingCode)
//sketchRef.addValueEventListener(object : ValueEventListener {
//    override fun onDataChange(snapshot: DataSnapshot) {
//        val segments = snapshot.child("segments").children
//        for (seg in segments) {
//            var color = seg.child("color-0d7_KjU").getValue<Long>()
//            var path = AndroidPath(seg.child("path").getValue<Path>()!!)
//            var width = seg.child("width").getValue<Float>()
//            println(color)
//            println(path)
//            println(path.getBounds())
//            println(width)
//            val newSeg = Segment(
//                color = Color(color!!),
//                path = path,
//                width = width!!
//            )
//            segments.plus(newSeg)
//
//        }