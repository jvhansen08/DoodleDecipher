package com.jaredaaronlogan.myapplication.ui.models

class Drawing(
    val xCollection: HashMap<String, ArrayList<Float>>,
    val yCollection: HashMap<String, ArrayList<Float>>,
    val colorCollection: HashMap<String, Int>,
    val widthCollection: HashMap<String, Float>,
    val indexCounter: Int,
    val sequenceId: String
) {

}