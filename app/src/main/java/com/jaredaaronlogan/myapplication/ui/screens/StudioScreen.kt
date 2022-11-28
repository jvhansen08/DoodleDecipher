package com.jaredaaronlogan.myapplication.ui.screens

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.components.ColorOption
import com.jaredaaronlogan.myapplication.ui.components.WidthOption
import com.jaredaaronlogan.myapplication.ui.viewmodels.StudioViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StudioScreen(navController: NavController) {
    val action: MutableState<Any?> = remember {  mutableStateOf(null) }
    var number by remember { mutableStateOf(0) }
    val viewModel: StudioViewModel = viewModel()
    val state = viewModel.uiState
    var choosingColor by remember { mutableStateOf(false) }
    var choosingWidth by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Top bar
        Row(modifier = Modifier
            .fillMaxHeight(.1f)
            .fillMaxWidth()
            .background(color = Color(0xFFfCD5CE)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.5f)
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxHeight(.75f)
                            .fillMaxWidth(.5f)
                            .padding(3.dp),
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back",)
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxHeight(.75f)
                            .fillMaxWidth(1f)
                            .padding(3.dp),
                        onClick = {
                            viewModel.saveImage()
                            navController.popBackStack()
                        },
                    ) {
                        Text(text = "Save")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxHeight(.75f)
                            .fillMaxWidth(.5f)
                            .padding(3.dp),
                        onClick = {
                            state.segments.removeAt(state.segments.size - 1)
                            number--
                        }
                    ) {
                        Text(text = "Undo")
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxHeight(.75f)
                            .fillMaxWidth(1f)
                            .padding(3.dp),
                        onClick = {
                            state.segments.clear()
                        }
                    ) {
                        Text(text = "Clear")
                    }
                }
            }
        }

        //Below the top bar
        Box(modifier = Modifier
            .fillMaxHeight(.8f)
            .fillMaxWidth(1f)
            .background(Color.White),
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                action.value = it
                                state.segment
                                    .path
                                    .moveTo(it.x, it.y)
                                number++
                            }
                            MotionEvent.ACTION_MOVE -> {
                                action.value = it
                                state.segment
                                    .path
                                    .lineTo(it.x, it.y)
                                number++
                            }
                            MotionEvent.ACTION_UP -> {
                                viewModel.saveSegment()
                            }
                        }
                        true
                    }
            ) {
                state.segments.forEach() {
                    drawPath(
                        path = it.path,
                        color = it.color,
                        alpha = 1f,
                        style = Stroke(width = it.width, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                }
                action.value?.let {
                    drawPath(
                        path = state.segment.path,
                        color = state.segment.color,
                        alpha = 1f,
                        style = Stroke(width = state.segment.width, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                }
                number--
            }
        }
        Row(
            modifier = Modifier
                .background(Color(0xFFfCD5CE))
                .fillMaxSize(1f)
        ) {
            Column() {
                Row() {
                    AnimatedVisibility(
                        visible = !choosingColor && !choosingWidth,
                    ) {
                        Button(
                            onClick = {
                                choosingColor = !choosingColor
                            }
                        ) {
                            Text(text = "Change color")
                        }
                    }
                    AnimatedVisibility(
                        visible = choosingColor,
                    ) {
                        Row() {
                            Column() {
                                ColorOptionRow(viewModel, state.topRowColors)
                                ColorOptionRow(viewModel, state.secondRowColors)
                                ColorOptionRow(viewModel, state.thirdRowColors)
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(1f)
                                    .padding(15.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End
                            ) {
                                Button(onClick = { choosingColor = !choosingColor }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Collapse colors",)
                                }
                            }
                        }
                    }
                }
                Row {
                    AnimatedVisibility(
                        visible = !choosingWidth && !choosingColor,
                    ) {
                        Button(
                            onClick = {
                                choosingWidth = !choosingWidth
                            }
                        ) {
                            Text(text = "Change Width")
                        }
                    }
                    AnimatedVisibility(
                        visible = choosingWidth,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight(1f)
                                    .fillMaxWidth(.8f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    WidthOption(color = state.penColor, size = 20f) {
                                        viewModel.changeWidth(15f)
                                    }
                                    WidthOption(color = state.penColor, size = 25f) {
                                        viewModel.changeWidth(25f)
                                    }
                                    WidthOption(color = state.penColor, size = 30f) {
                                        viewModel.changeWidth(40f)
                                    }
                                    WidthOption(color = state.penColor, size = 35f) {
                                        viewModel.changeWidth(65f)
                                    }
                                    WidthOption(color = state.penColor, size = 45f) {
                                        viewModel.changeWidth(100f)
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(1f)
                                    .padding(15.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End
                            ) {
                                Button(onClick = { choosingWidth = !choosingWidth }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Collapse widths",)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorOptionRow(viewModel: StudioViewModel, colors: List<Color>) {
    Row {
        for (color in colors) {
            ColorOption(color = color) {
                viewModel.changeColor(color)
            }
        }
    }
}