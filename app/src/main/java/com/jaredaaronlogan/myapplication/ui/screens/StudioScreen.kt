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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.components.ColorOption
import com.jaredaaronlogan.myapplication.ui.components.WidthOption
import com.jaredaaronlogan.myapplication.ui.viewmodels.StudioViewModel
import org.intellij.lang.annotations.JdkConstants


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
                            viewModel.redo()
                        },
                    ) {
                        Text(text = "Redo")
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
                            viewModel.undo()
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
                            viewModel.clear()
                        }
                    ) {
                        Text(text = "Clear")
                    }
                }
            }
        }

        //Below the top bar
        Row(
            modifier = Modifier
                .fillMaxHeight(.8f)
                .fillMaxWidth(1f)
                .background(Color(0xFFFAFAFA)),
            verticalAlignment = Alignment.CenterVertically
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
                                state.segment.path.lineTo(it.x, it.y)
                                number++
                            }
                            MotionEvent.ACTION_MOVE -> {
                                action.value = it
                                if (it.y >= 0) {
                                    println(it.y)
                                    state.segment
                                        .path
                                        .lineTo(it.x, it.y)
                                }
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
            AnimatedVisibility(
                visible = !choosingColor && !choosingWidth,
            ) {
                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(.5f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxHeight(.5f),
                            onClick = {
                                choosingColor = !choosingColor
                            }
                        ) {
                            Text(text = "Change color")
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.fillMaxWidth(.5f)) {
                            Button(
                                modifier = Modifier
                                    .fillMaxHeight(.5f),
                                onClick = {
                                    choosingWidth = !choosingWidth
                                }
                            ) {
                                Text(text = "Change Width")
                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth(1f)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(1f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { viewModel.saveImage() }
                                ) {
                                    Text("Send")
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = choosingColor,
            ) {
                Row() {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                    ) {
                        ColorOptionRow(viewModel, state.topRowColors, .333333f)
                        ColorOptionRow(viewModel, state.secondRowColors, .5f)
                        ColorOptionRow(viewModel, state.thirdRowColors, 1f)
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
            Row {
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
                                val configuration = LocalConfiguration.current
                                val screenWidth = configuration.screenWidthDp
                                Spacer(modifier = Modifier.width(5.dp))
                                WidthOption(color = state.penColor, size = 20f, screenWidth.toFloat()) {
                                    viewModel.changeWidth(15f)
                                }
                                WidthOption(color = state.penColor, size = 25f, screenWidth.toFloat()) {
                                    viewModel.changeWidth(25f)
                                }
                                WidthOption(color = state.penColor, size = 30f, screenWidth.toFloat()) {
                                    viewModel.changeWidth(40f)
                                }
                                WidthOption(color = state.penColor, size = 37f, screenWidth.toFloat()) {
                                    viewModel.changeWidth(65f)
                                }
                                WidthOption(color = state.penColor, size = 45f, screenWidth.toFloat()) {
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

@Composable
fun ColorOptionRow(viewModel: StudioViewModel, colors: List<Color>, heightRatio: Float) {
    Row(
        modifier = Modifier
            .fillMaxHeight(heightRatio)
            .fillMaxWidth(1f)
    ) {
        var counter = 7f
        var widthRatio = 1f/counter
        for (color in colors) {
            ColorOption(color = color, width = widthRatio) {
                viewModel.changeColor(color)
            }
            counter--
            widthRatio = 1f / counter
        }
    }
}