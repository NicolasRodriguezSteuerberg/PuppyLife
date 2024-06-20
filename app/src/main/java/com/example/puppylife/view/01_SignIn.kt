package com.example.puppylife.view

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.puppylife.R
import com.example.puppylife.viewmodel.LogInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun RegisterScreen(viewModel: LogInViewModel, navController: NavController, ) {
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        Log.d("Register", "${viewModel.inProcess.value}")
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            viewModel.signUpGoogle(
                account,
                onSuccess = { authResult ->
                    navigateToNoBack(navController, DestinationScreen.Logged.route)
                    viewModel.inProcess.value = false
                    Log.d("Register", " Navegacion ${viewModel.inProcess.value}")
                    Log.d("Register", "Firebase Auth with Google successful: ${authResult.user?.email}")
                },
                onFailure = { exception ->
                    viewModel.inProcess.value = false
                    Log.d("Register", "${viewModel.inProcess.value}")
                    Log.e("Register", "Firebase Auth with Google failed", exception)
                }
            )
        } catch (e: ApiException) {
            viewModel.inProcess.value = false
            Log.d("Register", "${viewModel.inProcess.value}")
            Log.e("Register", "Google sign-in failed", e)
        }
        Log.d("Register", "${viewModel.inProcess.value}")
    }

    CheckSingedIn(navController = navController)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E0E0)),
        contentAlignment = Alignment.Center
    ){
        /*
        TextField(
            value = DataSign.email.value,
            onValueChange = {DataSign.email.value = it}
        )
        */
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = {
                    viewModel.inProcess.value = true
                    val signInIntent = viewModel.getGoogleSignInClient().signInIntent
                    googleSignInLauncher.launch(signInIntent)
                },
                modifier = Modifier
                    .fillMaxWidth(0.20f)
                    .fillMaxHeight(0.10f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "Google Logo"
                )
            }

            Button(onClick = {
                viewModel.signOut()
            }) {
                Text(
                    text = "Sign Out",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
    if(viewModel.inProcess.value){
        CommonProgressBar()
    }
}
