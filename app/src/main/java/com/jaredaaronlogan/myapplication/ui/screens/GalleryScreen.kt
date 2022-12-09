package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.viewmodels.GalleryViewModel

@Composable
fun GalleryScreen(navController: NavController) {
    val viewModel: GalleryViewModel = viewModel()
    val state = viewModel.uiState
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp.value
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.value
    LaunchedEffect(true) {
        viewModel.getImage("z85zaEM15Sa8IIiGs2Zxpp6wDK220")
    }


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
                    for (i in 0 until state.indexCounter) {
                        println("I'm here!")
                        val path = Path()
                        val xVals = state.xCollectionValue[i]
                        val yVals = state.yCollectionValue[i]
                        path.moveTo(xVals[0] / screenWidth, yVals[0] / screenHeight)
                        for (j in 1 until xVals.size) {
                            path.lineTo(xVals[j] / screenWidth, yVals[j] / screenHeight)
                        }
                        drawPath(
                            path = path,
                            color = Color(state._colorCollectionValue[i]),
                            alpha = 1f,
                            style = Stroke(width = state._widthCollectionValue[i], cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                        println(state._colorCollectionValue[0])
                    }
                }
            }
        }
    }
}