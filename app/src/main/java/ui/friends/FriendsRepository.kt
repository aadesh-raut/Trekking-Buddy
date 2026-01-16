package com.example.trekkingbuddy.ui.friends

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FriendsRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchSuggestedFriends(): List<UserModel> {

        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        // 1️⃣ Get my selected location
        val currentUserDoc = db.collection("users")
            .document(currentUserId)
            .get()
            .await()

        val myLocation =
            currentUserDoc.getString("selectedLocation") ?: return emptyList()

        // 2️⃣ Get accepted friends
        val friendsSnapshot = db.collection("friends")
            .document(currentUserId)
            .collection("list")
            .get()
            .await()

        val friendIds = friendsSnapshot.documents.map { it.id }.toSet()

        // 3️⃣ Get incoming requests
        val incomingRequestsSnapshot = db.collection("friend_requests")
            .document(currentUserId)
            .collection("requests")
            .get()
            .await()

        val incomingRequestIds =
            incomingRequestsSnapshot.documents.map { it.id }.toSet()

        // 4️⃣ Get sent requests
        val sentRequestsSnapshot = db.collection("friend_requests")
            .document(currentUserId)
            .collection("sent")
            .get()
            .await()

        val sentRequestIds =
            sentRequestsSnapshot.documents.map { it.id }.toSet()

        // 5️⃣ Fetch users with same location
        val usersSnapshot = db.collection("users")
            .whereEqualTo("selectedLocation", myLocation)
            .get()
            .await()

        // 6️⃣ FINAL FILTER
        return usersSnapshot.documents
            .filter { doc ->
                val uid = doc.id

                uid != currentUserId &&
                        uid !in friendIds &&
                        uid !in incomingRequestIds &&
                        uid !in sentRequestIds
            }
            .map { doc ->
                UserModel(
                    uid = doc.id,
                    username = doc.getString("username") ?: "Unknown",
                    selectedLocation = doc.getString("selectedLocation") ?: ""
                )
            }
    }
}




