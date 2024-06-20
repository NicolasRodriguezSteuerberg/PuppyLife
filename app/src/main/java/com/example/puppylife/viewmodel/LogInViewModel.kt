package com.example.puppylife.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puppylife.data.DataSign
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    application: Application,
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
): AndroidViewModel(application) {
    val inProcess = mutableStateOf(false)

    init {
        // Al iniciar la app, se verifica si el usuario ya está logueado
        val currentUser = auth.currentUser
        // Si currentUser es diferente de null, significa que el usuario ya está logueado
        DataSign.signIn.value = currentUser != null
        /*
        currentUser?.uid?.let {
            getUserData(it)
        }
         */
    }

    fun getGoogleSignInClient() = googleSignInClient

    fun signUpGoogle(account: GoogleSignInAccount, onSuccess: (AuthResult) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try{
                val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                val authResult = auth.signInWithCredential(credential).await()
                onSuccess(authResult)
            } catch (e: Exception){
                onFailure(e)
            }
        }
    }

    fun signOut() {
        inProcess.value = true
        DataSign.signIn.value = false
        Log.d("LogInViewModel", "${auth.currentUser} and ${googleSignInClient}")
        auth.signOut()
        googleSignInClient.signOut()
        inProcess.value = false
        Log.d("LogInViewModel", "signOut: signed out ${auth.currentUser} and ${googleSignInClient}")
    }

    fun signIn(email: String, password: String) {
        // ToDo: Iniciar sesión con email y contraseña
    }

    private fun getUserData(uid: String) {
        //ToDo: Obtener los datos del usuario
    }
}