package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.viewmodels.EndViewModel

@Composable
fun EndScreen(navController: NavController, gameId: String) {
    val viewModel: EndViewModel = viewModel()
    val state = viewModel.uiState
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp.value
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.value

    LaunchedEffect(true) {
        viewModel.initialize(gameId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.round != -1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.1f)
            ) {
                Text(text = "This prompt:")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                viewModel.getPrompt(gameId)
                Text(text = state.prompt)
            }
            Row() {
                Text(text = "Was followed with this drawing:")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(.8f)
                        .background(Color.White)
                ) {
                    viewModel.getDrawing(gameId)
                    Canvas(modifier = Modifier.fillMaxSize()){
                        for (i in 0 until state.indexCounter) {
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
                        }
                    }
                }
            }
            Column() {
                if (state.round >= 2 || state.round == -1) {
                    Button(onClick = { viewModel.moveBackSequence() }) {
                        Text(text = "Back")
                    }
                }
                Button(onClick = { viewModel.moveToNextSequence() }) {
                    Text(text = "Next")
                }
            }
        } else {
            Text(text = "The end!")
        }
        Button(onClick = { navController.navigate(Routes.Home.route) }) {
            Text(text = "Home")
        }
    }
}