package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.repositories.GameStateRepo
import com.jaredaaronlogan.myapplication.ui.repositories.UserRepository

class EndState {
    var colorCollectionIndex by mutableStateOf("0")
    val _colorCollectionValue = mutableStateListOf<Int>()
    val colorCollectionValue: List<Int> get() = _colorCollectionValue
    var indexCounter by mutableStateOf(0)
    var _widthCollectionValue = mutableStateListOf<Float>()
    val widthCollectionValue: List<Float> get() = _widthCollectionValue
    var xCollectionValue = mutableStateListOf<ArrayList<Float>>()
    var yCollectionValue = mutableStateListOf<ArrayList<Float>>()

    var prevPlayerId = ""
    var prompt by mutableStateOf("")
    var drawing = mutableStateOf( Drawing() )
}

class EndViewModel(application: Application): AndroidViewModel(application) {
    val uiState = EndState()

    fun initialize(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val maxRounds = snapshot.child("maxRounds").getValue<Int>()!!
                val lastDrawingIndex = if (maxRounds % 2 == 1) maxRounds else maxRounds - 1
                val userId = UserRepository.getCurrentUserId()!!
                uiState.prompt = snapshot.child("promptsMap").child("0").child(userId).getValue<String>() ?: ""

                uiState.prevPlayerId = snapshot.child("playerMap").child(UserRepository.getCurrentUserId()!!).getValue<String>()!!
                for (color in snapshot.child("drawingsMap").child((lastDrawingIndex).toString()).child(uiState.prevPlayerId).child("colorCollection").children) {
                    uiState.colorCollectionIndex = color.key.toString()
                    uiState._colorCollectionValue.add(color.getValue<Int>()!!)
                }
                uiState.indexCounter = snapshot.child("drawingsMap").child((lastDrawingIndex).toString()).child(uiState.prevPlayerId).child("indexCounter").getValue<Int>() ?: 0
                for (width in snapshot.child("drawingsMap").child((lastDrawingIndex).toString()).child(uiState.prevPlayerId).child("widthCollection").children) {
                    uiState._widthCollectionValue.add(width.getValue<Float>()!!)
                }
                for (xValues in snapshot.child("drawingsMap").child((lastDrawingIndex).toString()).child(uiState.prevPlayerId).child("xcollection").children) {
                    val tempArrayX = ArrayList<Float>()
                    for (xVal in xValues.children) {
                        tempArrayX.add(xVal.getValue<Float>() ?: 0f)
                    }
                    uiState.xCollectionValue.add(tempArrayX)
                }
                for (yValues in snapshot.child("drawingsMap").child((lastDrawingIndex).toString()).child(uiState.prevPlayerId).child("ycollection").children) {
                    val tempArrayY = ArrayList<Float>()
                    for (yVal in yValues.children) {
                        tempArrayY.add(yVal.getValue<Float>() ?: 0f)
                    }
                    uiState.yCollectionValue.add(tempArrayY)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}