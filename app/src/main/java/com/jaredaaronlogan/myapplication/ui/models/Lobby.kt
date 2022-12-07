package com.jaredaaronlogan.myapplication.ui.models

data class Lobby(
    val id: String?,
    val hostId: String?,
    val joinCode: String?,
    var players: List<Player>?,
)