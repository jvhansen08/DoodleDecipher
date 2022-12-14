package com.jaredaaronlogan.myapplication.ui.components

class Drawing(
    val xCollection: HashMap<String, ArrayList<Float>> ?= null,
    val yCollection: HashMap<String, ArrayList<Float>> ?= null,
    val colorCollection: HashMap<String, Int> ?= null,
    val widthCollection: HashMap<String, Float> ?= null,
    val indexCounter: Int ?= 0,
    val id: String ?= ""
) {

}