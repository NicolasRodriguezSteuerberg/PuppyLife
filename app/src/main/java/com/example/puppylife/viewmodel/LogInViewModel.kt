package com.example.puppylife.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.puppylife.data.Data
import com.example.puppylife.model.Firebase.createUser
import com.example.puppylife.model.Firebase.getUserFirebase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    application: Application,
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val firestore: FirebaseFirestore
): AndroidViewModel(application) {

    init {
        // Al iniciar la app, se verifica si el usuario ya está logueado
        val currentUser = auth.currentUser
        // Si currentUser es diferente de null, significa que el usuario ya está logueado
        Data.signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun getGoogleSignInClient() = googleSignInClient

    fun signUpGoogle(account: GoogleSignInAccount, onSuccess: (AuthResult) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try{
                val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                val authResult = auth.signInWithCredential(credential).await()
                Log.d("Register", "UID-id = ${account.id} and ${authResult.user?.uid} and is ${account.id == authResult.user?.uid} ")
                createUser(firestore, authResult.user?.uid!!, account.displayName!!, account.photoUrl.toString())
                onSuccess(authResult)
            } catch (e: Exception){
                onFailure(e)
            }
        }
    }

    fun signIn(email: String, password: String) {
        // ToDo: Iniciar sesión con email y contraseña
    }

    private fun getUserData(uid: String) {
        getUserFirebase(firestore = firestore,uid =uid)
    }
}