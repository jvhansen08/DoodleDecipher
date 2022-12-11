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
    var gameOver by mutableStateOf(false)
}

class WaitingViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WaitingScreenState()

    fun initialize(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val round = snapshot.child("roundCounter").getValue<Int>()!!
                val finalRound = snapshot.child("maxRounds").getValue<Int>()!!
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
                    if (round == finalRound) {
                        uiState.gameOver = true
                    }
                    gameRef.child("roundCounter").setValue(round + 1)
                    uiState.waiting = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}