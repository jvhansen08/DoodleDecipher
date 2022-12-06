package com.jaredaaronlogan.myapplication.ui.components

class Lobby(val id: String, joinCode: String, host: String) {
    var players: MutableList<Player> = mutableListOf()

    fun addPlayer(player: Player) {
        if (players.size >= 8) {
            throw Exception("Lobby is full")
        }
        players.add(player)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun startGame() {
        players.shuffle()
        val game = Game(players)
        game.start()
    }
}