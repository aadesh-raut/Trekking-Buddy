package com.example.trekkingbuddy.ui.friends

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FriendsListRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchMyFriends(): List<UserModel> {

        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        // 1️⃣ Fetch friend IDs
        val friendsSnapshot = db.collection("friends")
            .document(currentUserId)
            .collection("list") // ✅ MUST match acceptRequest()
            .get()
            .await()

        if (friendsSnapshot.isEmpty) return emptyList()

        // 2️⃣ Fetch each friend's user profile
        return friendsSnapshot.documents.mapNotNull { friendDoc ->
            val friendId = friendDoc.id

            val userDoc = db.collection("users")
                .document(friendId)
                .get()
                .await()

            if (!userDoc.exists()) return@mapNotNull null

            UserModel(
                uid = friendId,
                username = userDoc.getString("username") ?: "Unknown",
                selectedLocation = userDoc.getString("selectedLocation") ?: ""
            )
        }
    }
}


