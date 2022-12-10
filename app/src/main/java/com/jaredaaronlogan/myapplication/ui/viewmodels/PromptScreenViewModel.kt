package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class PromptScreenState {
    var prompt by mutableStateOf("")
}

class PromptScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = PromptScreenState()

    fun markAsReady() {

    }
}