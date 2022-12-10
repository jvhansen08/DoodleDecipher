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
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.repositories.GameStateRepo
import com.jaredaaronlogan.myapplication.ui.repositories.UserRepository
import kotlin.math.round

class GalleryScreenState {
    var colorCollectionIndex by mutableStateOf("0")
    val _colorCollectionValue = mutableStateListOf<Int>()
    val colorCollectionValue: List<Int> get() = _colorCollectionValue
    var indexCounter by mutableStateOf(0)
    var _widthCollectionValue = mutableStateListOf<Float>()
    val widthCollectionValue: List<Float> get() = _widthCollectionValue
    var xCollectionValue = mutableStateListOf<ArrayList<Float>>()
    var yCollectionValue = mutableStateListOf<ArrayList<Float>>()

    var guess by mutableStateOf("")
    var round = 0
    var nextPlayerId = ""
}


class GalleryViewModel(application: Application): AndroidViewModel(application) {
    val uiState = GalleryScreenState()

    fun initialize(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uiState.round = snapshot.child("roundCounter").getValue<Int>()!!
                uiState.nextPlayerId = snapshot.child("playersMap").child(UserRepository.getCurrentUserId()!!).getValue<String>()!!
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        getImage(gameId)
    }
    fun getImage(gameId: String) {
        val db = Firebase.database.reference
        val imageRef = db
            .child("games")
            .child(gameId)
            .child("drawingsMap")
            .child((uiState.round - 1).toString())
            .child(uiState.nextPlayerId)
        println(imageRef)
        imageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (color in snapshot.child("colorCollection").children) {
                    uiState.colorCollectionIndex = color.key.toString()
                    uiState._colorCollectionValue.add(color.getValue<Int>()!!)
                }
                uiState.indexCounter = snapshot.child("indexCounter").getValue<Int>() ?: 0
                for (width in snapshot.child("widthCollection").children) {
                    uiState._widthCollectionValue.add(width.getValue<Float>()!!)
                }
                for (xValues in snapshot.child("xcollection").children) {
                    val tempArrayX = ArrayList<Float>()
                    for (xVal in xValues.children) {
                        tempArrayX.add(xVal.getValue<Float>() ?: 0f)
                    }
                    uiState.xCollectionValue.add(tempArrayX)
                }
                for (yValues in snapshot.child("ycollection").children) {
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

    fun submitGuess(gameId: String) {
        GameStateRepo.submitPrompt(gameId, uiState.guess, uiState.round)
    }
}