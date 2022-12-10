package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.jaredaaronlogan.myapplication.ui.repositories.GameStateRepo

class GameScreenState {
    var round by mutableStateOf(0)
}

class GameViewModel(application: Application): AndroidViewModel(application) {
    val uiState = GameScreenState()

    fun initialize(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uiState.round = snapshot.child("roundCounter").getValue<Int>()!!
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}