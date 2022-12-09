package com.jaredaaronlogan.myapplication.ui.components

class Drawing(
    val xCollection: HashMap<String, ArrayList<Float>>,
    val yCollection: HashMap<String, ArrayList<Float>>,
    val colorCollection: HashMap<String, Int>,
    val widthCollection: HashMap<String, Float>,
    val indexCounter: Int,
    val id: String
) {

}