package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.screens.*

class GalleryScreenState {
    var colorCollectionIndex by mutableStateOf("0")
    val _colorCollectionValue = mutableStateListOf<Int>()
    val colorCollectionValue: List<Int> get() = _colorCollectionValue
    var indexCounter by mutableStateOf(0)
    var widthCollectionIndex by mutableStateOf("0")
    var _widthCollectionValue = mutableStateListOf<Float>()
    val widthCollectionValue: List<Float> get() = _widthCollectionValue
    var xCollectionIndex by mutableStateOf("0")
    var xCollectionValue = mutableStateListOf<ArrayList<Float>>()
    var yCollectionIndex by mutableStateOf("0")
    var yCollectionValue = mutableStateListOf<ArrayList<Float>>()
    var triggerRecomposition by mutableStateOf(0)
}


class GalleryViewModel(application: Application): AndroidViewModel(application) {
    val uiState = GalleryScreenState()

    fun getImage(id: String) {
        val db = Firebase.database
        val imageRef = db.getReference("drawings").child(id)
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

                println(uiState.colorCollectionValue[0])
                println(uiState.widthCollectionValue[0])
                println(uiState.xCollectionValue[0])
                println(uiState.yCollectionValue[0])
                println(uiState.indexCounter)
                uiState.triggerRecomposition--
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}