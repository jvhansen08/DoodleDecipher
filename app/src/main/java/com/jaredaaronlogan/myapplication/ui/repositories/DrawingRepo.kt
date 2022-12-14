package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.components.Drawing

object DrawingRepo {
    val db = Firebase.database
    fun saveImage(drawing: Drawing) {
        val drawingRef = db.getReference("drawings")
        drawingRef.child(drawing.id!!).setValue(drawing)
    }

    fun getImage(id: String) {
        val imageRef = db.getReference("drawings").child(id)
        imageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val colorInfo = snapshot.child("colorCollection").children
                val indexCounter: Long = snapshot.child("indexCounter").value as Long
                val widthCollection = snapshot.child("widthCollection").value
                val xCollection = snapshot.child("xCollection").value
                val yCollection = snapshot.child("yCollection").value
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}