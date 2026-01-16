package com.example.trekkingbuddy.ui.preferences

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

private val db = FirebaseFirestore.getInstance()
private val auth = FirebaseAuth.getInstance()

// ------------------------------------------------------
// SAVE SELECTED LOCATION
// ------------------------------------------------------
suspend fun saveSelectedLocationToFirebase(location: String) {
    val userId = auth.currentUser?.uid ?: return

    // ✅ 1. Save at ROOT USER LEVEL (for matching users)
    db.collection("users")
        .document(userId)
        .set(
            mapOf("selectedLocation" to location),
            SetOptions.merge()
        )
        .await()

    // ✅ 2. Save in preference sub-collection (UI / history)
    db.collection("users")
        .document(userId)
        .collection("preference")
        .document("data")
        .set(
            mapOf("selectedLocation" to location),
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

