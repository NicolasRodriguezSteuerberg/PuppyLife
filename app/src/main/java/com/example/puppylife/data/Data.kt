package com.example.puppylife.data

import androidx.compose.runtime.mutableStateOf

object Data {
    val signIn = mutableStateOf(false)
    val inProcess = mutableStateOf(false)
    val user = mutableStateOf<User?>(null)
    val userID = mutableStateOf<String?>(null)
    val userName = mutableStateOf<String?>(null)
    val photoUrl = mutableStateOf<String?>(null)
}