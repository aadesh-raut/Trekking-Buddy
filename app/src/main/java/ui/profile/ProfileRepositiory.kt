package com.example.trekkingbuddy.data

import android.net.Uri
import com.example.trekkingbuddy.ui.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.example.trekkingbuddy.ui.profile.CompletedTrek
import com.google.firebase.Timestamp
import com.example.trekkingbuddy.ui.location.TrekLocationPoint





object ProfileRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getUserProfile(onResult: (UserProfile) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                val profile = doc.toObject(UserProfile::class.java)
                onResult(profile ?: UserProfile())
            }
    }

    fun saveUserProfile(profile: UserProfile) {
        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .set(profile)
    }

    // 📸 PROFILE IMAGE UPLOAD
    fun uploadProfileImage(
        imageUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return

        val ref = storage.reference
            .child("profile_images")
            .child("$uid.jpg")

        ref.putFile(imageUri)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                ref.downloadUrl
            }
            .addOnSuccessListener { uri ->
                val imageUrl = uri.toString()

                firestore.collection("users")
                    .document(uid)
                    .update("photoUrl", imageUrl)

                onSuccess(imageUrl)
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    fun markTrekAsCompleted(
        trekId: String,
        trekName: String
    ) {
        val uid = auth.currentUser?.uid ?: return

        val trek = CompletedTrek(
            id = trekId,
            trekName = trekName,
            completedOn = Timestamp.now()
        )

        firestore.collection("users")
            .document(uid)
            .collection("completedTreks")
            .document(trekId)
            .set(trek)
    }

    fun getCompletedTreks(onResult: (List<CompletedTrek>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .collection("completedTreks")
            .get()
            .addOnSuccessListener { snapshot ->
                val treks = snapshot.documents.map { doc ->
                    CompletedTrek(
                        id = doc.id,
                        trekName = doc.getString("trekName") ?: "",
                        completedOn = doc.getTimestamp("completedOn") ?: Timestamp.now()
                    )
                }
                onResult(treks)
            }
    }


    fun removeCompletedTrek(
        trekId: String,
        onSuccess: () -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .collection("completedTreks")
            .document(trekId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
    }
    fun saveTrekPath(
        trekId: String,
        path: List<TrekLocationPoint>
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val firestore = FirebaseFirestore.getInstance()

        val data = mapOf(
            "startedAt" to path.firstOrNull()?.timestamp,
            "endedAt" to path.lastOrNull()?.timestamp,
            "path" to path
        )

        firestore.collection("users")
            .document(uid)
            .collection("trekPaths")
            .document(trekId)
            .set(data)
    }






}



