package com.jaredaaronlogan.myapplication.ui.models

data class Player(
    val id: String? = "",
    var screenName: String = "",
    val score: Int? = 0,
    var host: Boolean = false,
    val ready: Boolean? = false,
)