package com.example.trekkingbuddy.ui.preferences

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FieldValue
private val db = FirebaseFirestore.getInstance()
private val auth = FirebaseAuth.getInstance()

// ------------------------------------------------------
// SAVE SELECTED LOCATION
// ------------------------------------------------------
suspend fun saveSelectedLocationToFirebase(locationName: String) {
    val user = FirebaseAuth.getInstance().currentUser
        ?: throw IllegalStateException("User not logged in")

    val db = FirebaseFirestore.getInstance()

    db.collection("users")
        .document(user.uid)
        .set(
            mapOf(
                "selectedLocation" to locationName, // ✅ FIX HERE
                "updatedAt" to FieldValue.serverTimestamp()
            ),
            SetOptions.merge()
        )
        .await()
}



// ------------------------------------------------------
// FETCH SELECTED LOCATION
// ------------------------------------------------------
suspend fun fetchSelectedLocationFromFirebase(): String? {
    val userId = auth.currentUser?.uid ?: return null

    val document = db.collection("users")
        .document(userId)
        .get()
        .await()

    return document.getString("selectedLocation")
}

// ------------------------------------------------------
// CLEAR SELECTED LOCATION
// ------------------------------------------------------
suspend fun clearSelectedLocationFromFirebase() {
    val userId = auth.currentUser?.uid ?: return

    db.collection("users")
        .document(userId)
        .update("selectedLocation", "")
        .await()
}

