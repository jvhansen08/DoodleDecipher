package com.jaredaaronlogan.myapplication.ui.models

import com.jaredaaronlogan.myapplication.ui.components.Drawing

data class Game(
    val gameID: String? = "",
    val numPlayers: Int = 0,
    val playerMap: Map<String, String>? = null,
    val maxRounds: Int = 0,
    val roundCounter: Int = 0,
    val drawingsMap: Map<Int, Map<String, Drawing>>? = null,
    val promptsMap: Map<Int, Map<String, String>>? = null,
)