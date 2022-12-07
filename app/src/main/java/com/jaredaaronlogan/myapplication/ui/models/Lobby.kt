package com.jaredaaronlogan.myapplication.ui.models

data class Lobby(
    val id: String?,
    val hostId: String?,
    val joinCode: String?,
    val players: List<Player>?,
)