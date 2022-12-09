package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.AndroidViewModel
import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.components.Segment
import com.jaredaaronlogan.myapplication.ui.repositories.DrawingRepo
import com.jaredaaronlogan.myapplication.ui.repositories.UserRepository
import com.jaredaaronlogan.myapplication.ui.theme.*
import java.util.*
import kotlin.collections.ArrayList

class StudioScreenState {
    val history = LinkedList<HistoryEntry>()

    var yValuesPath = ArrayList<Float>()
    var xValuesPath = ArrayList<Float>()
    val yCollection = HashMap<String, ArrayList<Float>>()
    val xCollection = HashMap<String, ArrayList<Float>>()
    val colorCollection = HashMap<String, Int>()
    val widthCollection = HashMap<String, Float>()
    var indexCounter = 0
    var drawingCount = 0


    var cleared = false
    var penColor = black1.toInt()
    var width = 10f
    var segment = Segment(Path(), Color(penColor), width,)
    val topRowColors = listOf(
        black1.toInt(),
        pink1.toInt(),
        purple1.toInt(),
        blue1.toInt(),
        green1.toInt(),
        white.toInt(),
    )
    val secondRowColors = listOf(
        red2.toInt(),
        pink2.toInt(),
        purple2.toInt(),
        blue2.toInt(),
        green2.toInt(),
        yellow2.toInt(),
    )
    val thirdRowColors = listOf(
        orange3.toInt(),
        red3.toInt(),
        purple3.toInt(),
        blue3.toInt(),
        green3.toInt(),
        yellow3.toInt(),
    )
}

class StudioViewModel(application: Application): AndroidViewModel(application) {
    val uiState = StudioScreenState()
    val realRepo = DrawingRepo
    fun saveSegment() {
        uiState.history.clear()
        uiState.xCollection[uiState.indexCounter.toString()] = uiState.xValuesPath
        uiState.yCollection[uiState.indexCounter.toString()] = uiState.yValuesPath
        uiState.colorCollection[uiState.indexCounter.toString()] = uiState.penColor
        uiState.widthCollection[uiState.indexCounter.toString()] = uiState.width
        uiState.indexCounter++
    }

    fun clear() {
        uiState.indexCounter = 0
        uiState.xValuesPath.clear()
        uiState.yValuesPath.clear()
        uiState.yCollection.clear()
        uiState.xCollection.clear()
    }

    fun saveImage() {
        val drawing = Drawing(
            xCollection = uiState.xCollection,
            yCollection = uiState.yCollection,
            colorCollection = uiState.colorCollection,
            widthCollection = uiState.widthCollection,
            indexCounter = uiState.indexCounter,
            id = UserRepository.getCurrentUserId() + uiState.drawingCount
        )
        realRepo.saveImage(drawing)
    }

    fun changeColor(newColor: Int) {
        uiState.penColor = newColor
        println(uiState.penColor.toString())
    }

    fun changeWidth(newWidth: Float){
        uiState.width = newWidth
    }

    fun undo() {
        if (uiState.indexCounter > 0) {
            val lastX = uiState.xCollection[(uiState.indexCounter - 1).toString()]
            val lastY = uiState.yCollection[(uiState.indexCounter - 1).toString()]
            val lastColor = uiState.colorCollection[(uiState.indexCounter - 1).toString()]
            val lastWidth = uiState.widthCollection[(uiState.indexCounter - 1).toString()]
            val currSegment = HistoryEntry(lastX!!, lastY!!, lastWidth!!, lastColor!!)
            uiState.xCollection[(uiState.indexCounter - 1).toString()]!!.clear()
            uiState.yCollection[(uiState.indexCounter - 1).toString()]!!.clear()
            uiState.indexCounter--
            uiState.history.push(currSegment)
        }
    }

//    fun redo() {
//        if (uiState.history.size > 0) {
//            val mostRecent = uiState.history.pollFirst()
//            uiState.xCollection[uiState.indexCounter] = mostRecent.first()
//            uiState.yCollection[uiState.indexCounter] = mostRecent.first()
//            uiState.indexCounter++
//        }
//    }

}

class HistoryEntry(val xVals: ArrayList<Float>, val yVals: ArrayList<Float>, val width: Float, val color: Int) {
}