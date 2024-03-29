package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.jaredaaronlogan.myapplication.ui.repositories.GameStateRepo
import com.jaredaaronlogan.myapplication.ui.repositories.UserRepository

class PromptScreenState {
    var prompt by mutableStateOf("")
}

class PromptScreenViewModel(application: Application): AndroidViewModel(application) {
    fun submitPrompt(gameId: String, prompt: String, round: Int) {
        GameStateRepo.submitPrompt(gameId, prompt, round, UserRepository.getCurrentUserId()!!)
    }

    val uiState = PromptScreenState()

    fun markAsReady() {

    }
}