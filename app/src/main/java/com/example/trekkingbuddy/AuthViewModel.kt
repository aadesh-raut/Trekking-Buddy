package com.example.trekkingbuddy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                onSuccess()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Login failed")
            }
        }
    }

    // ---------------------------------------------------------
    // SIGNUP + SAVE USER DATA
    // ---------------------------------------------------------
    fun signup(
        email: String,
        password: String,
        username: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result =
                    auth.createUserWithEmailAndPassword(email, password).await()

                val uid = result.user?.uid ?: return@launch

                // 🔥 SAVE USER PROFILE IN FIRESTORE (ROOT LEVEL)
                firestore.collection("users")
                    .document(uid)
                    .set(
                        mapOf(
                            "username" to username,
                            "selectedLocation" to ""
                        )
                    )
                    .await()

                onSuccess()

            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Signup failed")
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}







