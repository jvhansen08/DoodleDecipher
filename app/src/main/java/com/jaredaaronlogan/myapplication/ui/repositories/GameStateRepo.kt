package com.jaredaaronlogan.myapplication.ui.repositories

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object GameStateRepo {
    val db = Firebase.database

    fun submitPrompt(gameId: String, prompt: String, round: Int) {
        db.getReference("games")
            .child(gameId)
            .child("promptsMap")
            .child(round.toString())
            .child(UserRepository.getCurrentUserId().toString())
            .setValue(prompt)
    }
}