package com.jaredaaronlogan.myapplication.ui.models

data class Player(
    val id: String? = "",
    val screenName: String = "",
    val score: Int? = 0,
    var host: Boolean = false,
    val ready: Boolean? = false,
)