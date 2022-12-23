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

    var round by mutableStateOf(0)
    var prompt by mutableStateOf("")
    var lastArtistId = ""
    var maxRounds = 0
}

class EndViewModel(application: Application) : AndroidViewModel(application) {
    val uiState = EndState()

    fun initialize(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                uiState.maxRounds = snapshot.child("maxRounds").getValue<Int>()!!
                val lastDrawingIndex = 1
                val userId = UserRepository.getCurrentUserId()!!
                for (drawing in snapshot.child("drawingsMap")
                    .child(lastDrawingIndex.toString()).children) {
                    if (drawing.child("sequenceId").getValue<String>() == userId) {
                        uiState.lastArtistId = drawing.key!!
                    }
                }
                uiState.prompt =
                    snapshot.child("promptsMap").child("0").child(userId).child("prompt")
                        .getValue<String>() ?: ""

                for (color in snapshot.child("drawingsMap").child((lastDrawingIndex).toString())
                    .child(uiState.lastArtistId).child("colorCollection").children) {
                    uiState.colorCollectionIndex = color.key.toString()
                    uiState._colorCollectionValue.add(color.getValue<Int>()!!)
                }
                uiState.indexCounter =
                    snapshot.child("drawingsMap").child((lastDrawingIndex).toString())
                        .child(uiState.lastArtistId).child("indexCounter").getValue<Int>() ?: 0
                for (width in snapshot.child("drawingsMap").child((lastDrawingIndex).toString())
                    .child(uiState.lastArtistId).child("widthCollection").children) {
                    uiState._widthCollectionValue.add(width.getValue<Float>()!!)
                }
                for (xValues in snapshot.child("drawingsMap").child((lastDrawingIndex).toString())
                    .child(uiState.lastArtistId).child("xcollection").children) {
                    val tempArrayX = ArrayList<Float>()
                    for (xVal in xValues.children) {
                        tempArrayX.add(xVal.getValue<Float>() ?: 0f)
                    }
                    uiState.xCollectionValue.add(tempArrayX)
                }
                for (yValues in snapshot.child("drawingsMap").child((lastDrawingIndex).toString())
                    .child(uiState.lastArtistId).child("ycollection").children) {
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

    fun moveToNextSequence() {
        uiState.round += 2
    }

    fun moveBackSequence() {
        uiState.round -= 2
    }

    fun getPrompt(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userId = UserRepository.getCurrentUserId() ?: return
                if (uiState.round != -1 && uiState.round <= uiState.maxRounds) {
                    for (prompt in snapshot.child("promptsMap")
                        .child((uiState.round).toString()).children) {
                        if (prompt.child("sequenceId").getValue<String>() == userId) {
                            uiState.prompt = prompt.child("prompt").getValue<String>()!!
                        }
                    }
                } else if (uiState.maxRounds != 0) {
                    uiState.round = -1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getDrawing(gameId: String) {
        val gameRef = GameStateRepo.db.getReference("games").child(gameId)
        gameRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userId = UserRepository.getCurrentUserId()!!
                val drawingIndex = uiState.round + 1
                uiState._colorCollectionValue.clear()
                uiState._widthCollectionValue.clear()
                uiState.xCollectionValue.clear()
                uiState.yCollectionValue.clear()

                if (uiState.round != -1 && drawingIndex <= uiState.maxRounds) {
                    for (drawing in snapshot.child("drawingsMap")
                        .child((drawingIndex).toString()).children) {
                        if (drawing.child("sequenceId").getValue<String>() == userId) {
                            uiState.lastArtistId = drawing.key!!
                        }
                    }
                    for (color in snapshot.child("drawingsMap").child((drawingIndex).toString())
                        .child(uiState.lastArtistId).child("colorCollection").children) {
                        uiState.colorCollectionIndex = color.key.toString()
                        uiState._colorCollectionValue.add(color.getValue<Int>()!!)
                    }
                    uiState.indexCounter =
                        snapshot.child("drawingsMap").child((drawingIndex).toString())
                            .child(uiState.lastArtistId).child("indexCounter").getValue<Int>() ?: 0
                    for (width in snapshot.child("drawingsMap").child((drawingIndex).toString())
                        .child(uiState.lastArtistId).child("widthCollection").children) {
                        uiState._widthCollectionValue.add(width.getValue<Float>()!!)
                    }
                    for (xValues in snapshot.child("drawingsMap").child((drawingIndex).toString())
                        .child(uiState.lastArtistId).child("xcollection").children) {
                        val tempArrayX = ArrayList<Float>()
                        for (xVal in xValues.children) {
                            tempArrayX.add(xVal.getValue<Float>() ?: 0f)
                        }
                        uiState.xCollectionValue.add(tempArrayX)
                    }
                    for (yValues in snapshot.child("drawingsMap").child((drawingIndex).toString())
                        .child(uiState.lastArtistId).child("ycollection").children) {
                        val tempArrayY = ArrayList<Float>()
                        for (yVal in yValues.children) {
                            tempArrayY.add(yVal.getValue<Float>() ?: 0f)
                        }
                        uiState.yCollectionValue.add(tempArrayY)
                    }
                } else if (uiState.maxRounds != 0) {
                    uiState.round = -1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}