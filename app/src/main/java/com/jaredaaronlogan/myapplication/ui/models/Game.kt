package com.jaredaaronlogan.myapplication.ui.models

data class Game(
    val gameID: String? = "",
    val numPlayers: Int = 0,
    val playerMap: Map<String, String>? = null,
    val maxRounds: Int = 0,
    val roundCounter: Int = 0,
    val drawingsMap: Map<String, Map<String, Drawing>>? = null,
    val promptsMap: Map<String, Map<String, String>>? = null,
)