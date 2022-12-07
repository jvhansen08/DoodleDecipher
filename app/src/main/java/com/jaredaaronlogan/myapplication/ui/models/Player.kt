package com.jaredaaronlogan.myapplication.ui.models

data class Player(
    val id: String?,
    val screenName: String,
    val score: Int?,
    var host: Boolean,
    val ready: Boolean?,
)