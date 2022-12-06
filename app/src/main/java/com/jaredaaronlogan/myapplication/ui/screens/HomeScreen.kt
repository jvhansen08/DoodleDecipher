package com.jaredaaronlogan.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jaredaaronlogan.myapplication.ui.navigation.Routes
import com.jaredaaronlogan.myapplication.ui.repositories.UserRepository

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFf8EDEB))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 250.dp),
                text = "Doodle Home",
                fontSize = 45.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("studio") },
            ) {
                Text(text = "Draw")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("gallery") },
            ) {
                Text(text = "Gallery")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate("lobby")
                },
            ) {
                Text(text = "Create a Lobby")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    UserRepository.signOutUser()
                    navController.navigate(Routes.Launch.route) },
            ) {
                Text(text = "Log out")
            }
        }
    }
}
