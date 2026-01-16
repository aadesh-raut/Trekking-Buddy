package com.example.trekkingbuddy.ui.friends

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FriendRequestRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // -------------------------------------------------
    // SEND FRIEND REQUEST ✅
    // -------------------------------------------------
    suspend fun sendFriendRequest(
        receiverId: String,
        receiverUsername: String
    ) {
        val sender = auth.currentUser ?: return
        val senderId = sender.uid

        val senderDoc = db.collection("users")
            .document(senderId)
            .get()
            .await()

        val senderUsername =
            senderDoc.getString("username") ?: "Unknown"

        val requestData = mapOf(
            "senderUsername" to senderUsername,
            "status" to "pending",
            "timestamp" to System.currentTimeMillis()
        )

        // Incoming request (receiver)
        db.collection("friend_requests")
            .document(receiverId)
            .collection("requests")
            .document(senderId)
            .set(requestData)
            .await()

        // Sent request (sender)
        db.collection("friend_requests")
            .document(senderId)
            .collection("sent")
            .document(receiverId)
            .set(mapOf("timestamp" to System.currentTimeMillis()))
            .await()
    }

    // -------------------------------------------------
    // FETCH INCOMING REQUESTS ✅
    // -------------------------------------------------
    suspend fun fetchIncomingRequests(): List<FriendRequestModel> {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        val snapshot = db.collection("friend_requests")
            .document(currentUserId)
            .collection("requests")
            .whereEqualTo("status", "pending")
            .get()
            .await()

        return snapshot.documents.map {
            FriendRequestModel(
                senderUid = it.id,
                senderUsername = it.getString("senderUsername") ?: "Unknown"
            )
        }
    }

    // -------------------------------------------------
    // ACCEPT REQUEST ✅
    // -------------------------------------------------
    suspend fun acceptRequest(senderUid: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        // 1️⃣ Add both users to friends
        db.collection("friends")
            .document(currentUserId)
            .collection("list")
            .document(senderUid)
            .set(mapOf("since" to System.currentTimeMillis()))
            .await()

        db.collection("friends")
            .document(senderUid)
            .collection("list")
            .document(currentUserId)
            .set(mapOf("since" to System.currentTimeMillis()))
            .await()

        // 2️⃣ Remove incoming request
        db.collection("friend_requests")
            .document(currentUserId)
            .collection("requests")
            .document(senderUid)
            .delete()
            .await()

        // 3️⃣ Remove sent request reference (SAFE CLEANUP)
        db.collection("friend_requests")
            .document(senderUid)
            .collection("sent")
            .document(currentUserId)
            .delete()
            .await()
    }

    // -------------------------------------------------
    // REJECT REQUEST ❌
    // -------------------------------------------------
    suspend fun rejectRequest(senderUid: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        // Remove incoming request
        db.collection("friend_requests")
            .document(currentUserId)
            .collection("requests")
            .document(senderUid)
            .delete()
            .await()

        // Remove sent request reference
        db.collection("friend_requests")
            .document(senderUid)
            .collection("sent")
            .document(currentUserId)
            .delete()
            .await()
    }
}




