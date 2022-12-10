package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class GameScreenState {
    var round by mutableStateOf(0)
}

class GameViewModel(application: Application): AndroidViewModel(application) {
    val uiState = GameScreenState()
}