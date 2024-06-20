package com.example.puppylife.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.puppylife.data.Data
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoggedViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val firestore: FirebaseFirestore
): ViewModel() {
    fun signOut(){
        Data.inProcess.value = true
        Data.signIn.value = false
        Log.d("LoggedInViewModel", "${auth.currentUser} and ${googleSignInClient}")
        auth.signOut()
        googleSignInClient.signOut()
        Data.inProcess.value = false
        Log.d("LoggedInViewModel", "signOut: signed out ${auth.currentUser} and ${googleSignInClient}")
    }
}