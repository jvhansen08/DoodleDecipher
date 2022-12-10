package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.animation.core.snap
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.models.Game
import com.jaredaaronlogan.myapplication.ui.repositories.GameStateRepo

class WaitingScreenState {
    var waiting by mutableStateOf(true)
}

class WaitingViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WaitingScreenState()

    fun initialize(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val round = snapshot.child("roundCounter").getValue<Int>()!!
                val playerCount = snapshot.child("numPlayers").getValue<Int>()!!
                var readyPlayers: Long
                if (round % 2 == 0) {
                    readyPlayers = snapshot
                        .child("promptsMap")
                        .child(round.toString())
                        .childrenCount
                } else {
                    readyPlayers = snapshot
                        .child("drawingsMap")
                        .child(round.toString())
                        .childrenCount
                }
                if (readyPlayers.toInt() == playerCount){
                    uiState.waiting = false
                    gameRef.child("hasNavigated").setValue(true)
                    gameRef.child("roundCounter").setValue(round + 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
//    fun stopListening(gameId: String) {
//        val db = GameStateRepo.db.reference.child("games").child(gameId)
//        var tempGame: Game
//        db.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                tempGame = Game(
//                    gameID = gameId,
//                    numPlayers = snapshot.child("numPlayers").getValue<Int>()!!,
//                    playerMap = snapshot.child("playerMap").getValue<Map<String, String>>()!!,
//                    maxRounds = snapshot.child("maxRounds").getValue<Int>()!!,
//                    roundCounter = snapshot.child("roundCounter").getValue<Int>()!!,
//                    drawingsMap = snapshot.child("drawingsMap").getValue<Map<String, Map<String, Drawing>>>(),
//                    promptsMap = snapshot.child("promptsMap").getValue<Map<String, Map<String, String>>>()
//                )
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//        db.removeValue()
//    }
}