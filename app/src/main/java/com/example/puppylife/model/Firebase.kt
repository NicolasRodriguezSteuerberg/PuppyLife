package com.example.puppylife.model

import com.example.puppylife.data.Data
import com.example.puppylife.data.User
import com.google.firebase.firestore.FirebaseFirestore

object Firebase{
    fun createUser(firestore: FirebaseFirestore, uid: String, name: String, photoUrl: String?){
        existUser(firestore, uid){ exist ->
            Data.user.value = User(uid, name, photoUrl)
            if(!exist){
                firestore.collection("users").document(uid).set(Data.user.value!!)
            }
        }
    }

    fun existUser(firestore: FirebaseFirestore, uid: String, callback: (Boolean) -> Unit){
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener {
                callback(it.exists())
            }
            .addOnFailureListener{
                callback(false)
            }
    }

    fun getUserFirebase(firestore: FirebaseFirestore, uid: String){
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener {
                Data.user.value = it.toObject(User::class.java)
            }
            .addOnFailureListener{
                Data.user.value = null
            }
    }

}