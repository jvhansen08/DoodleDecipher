package com.jaredaaronlogan.myapplication.ui.viewmodels

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.AndroidViewModel
import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.components.Segment
import com.jaredaaronlogan.myapplication.ui.repositories.TempRepo

class StudioScreenState {
    val segments = mutableStateListOf<Segment>()
    var penColor = Color.Black
    var width = 10f
    var segment = Segment(Path(), penColor, width,)
    val topRowColors = listOf(
        Color.Black,
        Color(0xFFEF88BE),
        Color(0xFFA87AF7),
        Color(0xFF9FFCFD),
        Color(0xFFA1FB8E),
        Color(0xFFEAEAEA),
    )
    val secondRowColors = listOf(
        Color(0xFF8E403A),
        Color(0xFFEA3680),
        Color(0xFF732BF5),
        Color(0xFF37B0B0),
        Color(0xFF6EEB49),
        Color(0xFFF8FF2F),
    )
    val thirdRowColors = listOf(
        Color(0xFFF07D28),
        Color(0xFFEB1006),
        Color(0xFF75197D),
        Color(0xFF0020E3),
        Color(0xFF419428),
        Color(0xFFF0D22E),
    )
}

class StudioViewModel(application: Application): AndroidViewModel(application) {
    val uiState = StudioScreenState()
    val repo = TempRepo
    fun saveSegment() {
        uiState.segments.add(uiState.segment)
        uiState.segment = Segment(Path(), uiState.penColor, uiState.width)
    }

    fun saveImage() {
        val drawing = Drawing(uiState.segments)
        repo.segments = uiState.segments
        repo.drawings.add(drawing)
    }

    fun changeColor(newColor: Color) {
        uiState.penColor = newColor
        uiState.segment = Segment(Path(), newColor, uiState.width)
    }

    fun changeWidth(newWidth: Float){
        uiState.width = newWidth
        uiState.segment = Segment(Path(), uiState.penColor, newWidth)
    }

}