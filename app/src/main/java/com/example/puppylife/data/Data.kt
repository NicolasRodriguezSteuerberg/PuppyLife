package com.example.puppylife.data

import androidx.compose.runtime.mutableStateOf

object DataSign {
    val signIn = mutableStateOf(false)
    val user = mutableStateOf<User?>(null)
    val email = mutableStateOf("")
    val password = mutableStateOf("")
}