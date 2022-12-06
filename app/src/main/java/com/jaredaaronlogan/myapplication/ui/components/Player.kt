package com.jaredaaronlogan.myapplication.ui.components

class Player(val id: String) {
    val name: String = generateRandomName()
//    val segments = ArrayList<Segment>()
//    var score: Int = 0
//    var currentPhrase: String = ""
//    var currentDrawing: Drawing = Drawing(segments)
//    var guess: String = ""
}

private fun generateRandomName(): String {
    val names = listOf(
        "Rainbow Raccoon",
        "Peanut Butter Panda",
        "Bouncy Beaver",
        "Captain Cheetah",
        "Lucky Lion",
        "Wacky Walrus",
        "Spunky Skunk",
        "Awesome Aardvark",
    )
    return names.random()
}