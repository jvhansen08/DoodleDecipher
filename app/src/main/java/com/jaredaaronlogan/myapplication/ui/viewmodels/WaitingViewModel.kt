package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class WaitingScreenState {
    var waiting by mutableStateOf(true)
}

class WaitingViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WaitingScreenState()
}