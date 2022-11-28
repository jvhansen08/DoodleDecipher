package com.jaredaaronlogan.myapplication.ui.repositories

import com.jaredaaronlogan.myapplication.ui.components.Drawing
import com.jaredaaronlogan.myapplication.ui.components.Segment

object TempRepo {
    var segments = mutableListOf<Segment>()
    val drawings = mutableListOf<Drawing>()
}