package com.example.puppylife.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.puppylife.viewmodel.LogInViewModel

@Composable
fun ProfileScreen(mainNavController: NavController, logInViewModel: LogInViewModel) {
    Box(modifier = Modifier.padding(8.dp)) {
        Column {
            Text(text = "Profile Screen")
            Button(
                onClick = {
                    logInViewModel.signOut()
                    navigateToNoBack(mainNavController, DestinationScreen.Register.route)
                }
            ) {
                Text(text = "Sign Out")
            }
        }
        if (logInViewModel.inProcess.value) {
            CommonProgressBar()
        }
    }
}