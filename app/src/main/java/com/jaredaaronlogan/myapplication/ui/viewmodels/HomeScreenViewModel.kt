package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.jaredaaronlogan.myapplication.ui.repositories.LobbyRepo

class HomeScreenState {
    var joinCode by mutableStateOf("")
}

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = HomeScreenState()
    fun createLobby(): String {
        val joinCode = LobbyRepo.createLobby()
        uiState.joinCode = joinCode
        return joinCode
    }

    fun joinLobby(joinCode: String) {
        LobbyRepo.joinLobby(joinCode)
    }
}
