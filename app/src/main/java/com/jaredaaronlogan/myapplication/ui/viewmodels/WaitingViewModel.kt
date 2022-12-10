package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
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
                val readyPlayers: Long
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
                if (readyPlayers.toInt() == playerCount ) uiState.waiting = false
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}