package com.example.trekkingbuddy.ui.preferences

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun fetchSelectedLocationFromFirebase(): String? {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return null
    val db = FirebaseFirestore.getInstance()

    val document = db.collection("users")
        .document(userId)
        .collection("preference")
        .document("data")
        .get()
        .await()

    return document.getString("selectedLocation")
}


