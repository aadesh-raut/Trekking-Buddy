package com.example.trekkingbuddy

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val authState = mutableStateOf("")

    // Callbacks assigned in NavGraph
    var onLoginSuccess: (() -> Unit)? = null
    var onSignupSuccess: (() -> Unit)? = null

    // ---------------------------------------------------------
    // LOGIN (used by LoginScreen)
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
    // SIGNUP (used by SignupScreen)
    // ---------------------------------------------------------
    fun signup(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
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





