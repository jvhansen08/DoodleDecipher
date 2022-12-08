package com.jaredaaronlogan.myapplication.ui.models

data class Lobby(
    val hostId: String? = "",
    val joinCode: String? = "",
    val players: Map<String, Player>? = null,
)