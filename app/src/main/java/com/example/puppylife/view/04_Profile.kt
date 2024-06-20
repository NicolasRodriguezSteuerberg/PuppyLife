package com.example.puppylife.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.puppylife.data.Data
import com.example.puppylife.viewmodel.LoggedViewModel

@Composable
fun ProfileScreen(mainNavController: NavController, loggedViewModel: LoggedViewModel) {
    Box(
        modifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            Box(Modifier.weight(1f)) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ){
                    ImageFromUri(uri = Data.user.value?.photoUrl, modifier = Modifier.size(100.dp))
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = Data.user.value?.userName ?: "User",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
            Button(
                onClick = {
                    loggedViewModel.signOut()
                    navigateToNoBack(mainNavController, DestinationScreen.Register.route)
                }, modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Sign Out")
            }
        }
        if (Data.inProcess.value) {
            CommonProgressBar()
        }
    }
}