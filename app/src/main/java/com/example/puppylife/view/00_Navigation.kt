package com.example.puppylife.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.puppylife.data.DataSign
import com.example.puppylife.viewmodel.LogInViewModel

sealed class DestinationScreen(var route: String){
    object Home: DestinationScreen("home")
    object Login: DestinationScreen("login")
    object Register: DestinationScreen("register")
}

@Composable
fun Navigation(padding: PaddingValues, onSignWithGoogleClicked: () -> Unit) {
    val navController = rememberNavController()
    var viewModel = hiltViewModel<LogInViewModel>()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.Register.route,
        modifier = Modifier.padding(padding),
    ) {
        composable(DestinationScreen.Register.route){
            RegisterScreen(navController = navController,viewModel = viewModel)
        }
        composable(DestinationScreen.Login.route){
            LogInScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.Home.route){
            HomeScreen()
        }
    }
}

@Composable
fun CheckSingedIn(navController: NavController){
    if(DataSign.signIn.value){
        // ToDo: Cambiar ruta
        navigateToNoBack(navController, DestinationScreen.Login.route)
    }
}

fun navigateToNoBack(navController: NavController, route: String){
    navController.navigate(route){
        popUpTo(0)
        launchSingleTop = true
    }
}

fun navigateToWithBack(navController: NavController, route: String){
    navController.navigate(route){
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressBar(){
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.Blue)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}