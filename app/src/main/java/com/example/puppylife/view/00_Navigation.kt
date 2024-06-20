package com.example.puppylife.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.puppylife.data.DataSign
import com.example.puppylife.viewmodel.LogInViewModel

sealed class DestinationScreen(var route: String){
    object Logged: DestinationScreen("logged")
    object Login: DestinationScreen("login")
    object Register: DestinationScreen("register")
}

@Composable
fun Navigation(padding: PaddingValues) {
    val navController = rememberNavController()
    var viewModel = hiltViewModel<LogInViewModel>()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.Register.route,
        modifier = Modifier.padding(padding).background(Color(0xFF000CEC)),
    ) {
        composable(DestinationScreen.Register.route){
            RegisterScreen(navController = navController,viewModel = viewModel)
        }
        composable(DestinationScreen.Login.route){
            LogInScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.Logged.route){
            LoggedScreen(mainNavController = navController, logViewModel = viewModel)
        }
    }
}

@Composable
fun CheckSingedIn(navController: NavController){
    if(DataSign.signIn.value){
        // ToDo: Cambiar ruta
        navigateToNoBack(navController, DestinationScreen.Logged.route)
    }
}
