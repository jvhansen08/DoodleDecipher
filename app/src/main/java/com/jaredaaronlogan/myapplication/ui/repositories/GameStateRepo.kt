package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jaredaaronlogan.myapplication.ui.models.Drawing

object GameStateRepo {
    val db = Firebase.database

    fun submitPrompt(gameId: String, prompt: String, round: Int, sequenceId: String) {
         val promptDb =
        db.getReference("games")
            .child(gameId)
            .child("promptsMap")
            .child(round.toString())
            .child(UserRepository.getCurrentUserId().toString())
        promptDb.child("prompt").setValue(prompt)
        promptDb.child("sequenceId").setValue(sequenceId)
    }

    fun submitDrawing(gameId: String, drawing: Drawing, round: Int) {
        db.getReference("games")
            .child(gameId)
            .child("drawingsMap")
            .child(round.toString())
            .child(UserRepository.getCurrentUserId().toString())
            .setValue(drawing)
    }
}