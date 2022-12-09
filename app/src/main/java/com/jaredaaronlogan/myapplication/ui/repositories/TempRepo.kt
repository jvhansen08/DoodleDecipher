package com.jaredaaronlogan.myapplication.ui.repositories

import androidx.compose.ui.graphics.Color
import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.components.Segment

object TempRepo {
    var yCollection = HashMap<String, ArrayList<Float>>()
    var xCollection = HashMap<String, ArrayList<Float>>()
    var colorCollection = HashMap<String, Int>()
    var widthCollection = HashMap<String, Float>()
    var indexCounter = 0
}