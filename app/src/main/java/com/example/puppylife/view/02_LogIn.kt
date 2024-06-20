package com.example.puppylife.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.puppylife.viewmodel.LogInViewModel

@Composable
fun LogInScreen(navController: NavController, viewModel: LogInViewModel){
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "You are logged in!"
        )

        Button(
            onClick = {
                viewModel.signOut()
                navigateToNoBack(navController, DestinationScreen.Register.route)
            },
            modifier = Modifier.padding(16.dp)
        ){
            Text("Log Out")
        }
    }
}