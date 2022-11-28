package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.repositories.TempRepo

@Composable
fun GalleryScreen(navController: NavController) {
    val repo = TempRepo
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Top bar
        Row(
            modifier = Modifier
                .fillMaxHeight(.1f)
                .fillMaxWidth()
                .background(color = Color(0xFFf8EDEB)),
        ) {
            Button(
                onClick = { navController.popBackStack() },
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back",)
            }
        }

        //Below the top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()){
                    repo.segments.forEach() {
                        println("Segment: $it")
                        drawPath(
                            path = it.path,
                            color = it.color,
                            alpha = 1f,
                            style = Stroke(width = it.width, cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                    }
                }
            }
        }
    }
}