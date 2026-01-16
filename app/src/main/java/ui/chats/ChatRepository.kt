package com.example.trekkingbuddy.ui.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object ChatRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun getChatId(friendUid: String): String {
        val myUid = auth.currentUser!!.uid
        return if (myUid < friendUid) {
            "${myUid}_${friendUid}"
        } else {
            "${friendUid}_${myUid}"
        }
    }

    suspend fun sendMessage(friendUid: String, message: String) {
        val myUid = auth.currentUser!!.uid
        val chatId = getChatId(friendUid)

        val messageData = mapOf(
            "senderId" to myUid,
            "text" to message,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(messageData)
    }

    fun listenForMessages(friendUid: String) = callbackFlow {
        val chatId = getChatId(friendUid)

        val listener = db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messages = snapshot.documents.mapNotNull {
                        it.toObject(ChatMessageModel::class.java)
                    }
                    trySend(messages)
                }
            }

        awaitClose { listener.remove() }
    }

}

