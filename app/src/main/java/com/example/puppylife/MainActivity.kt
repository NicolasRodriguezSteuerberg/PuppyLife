package com.example.puppylife

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.puppylife.ui.theme.PuppyLifeTheme
import com.example.puppylife.view.Navigation
import com.example.puppylife.viewmodel.LogInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: LogInViewModel by viewModels()

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            authViewModel.signUpGoogle(
                account,
                onSuccess = { authResult ->
                    Log.d("MainActivity", "Firebase Auth with Google successful: ${authResult.user?.email}")
                },
                onFailure = { exception ->
                    Log.e("MainActivity", "Firebase Auth with Google failed", exception)
                }
            )
        } catch (e: ApiException) {
            Log.e("MainActivity", "Google sign-in failed", e)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PuppyLifeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Color(0xFFFA0202)
                ) { innerPadding ->
                    Navigation(padding = innerPadding,
                        onSignWithGoogleClicked ={
                            val signInIntent = authViewModel.getGoogleSignInClient().signInIntent
                            googleSignInLauncher.launch(signInIntent)
                        }
                    )
                }
            }
        }
    }
}

@HiltAndroidApp
class Dagger: Application()
