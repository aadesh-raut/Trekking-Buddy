package com.example.trekkingbuddy

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val uid: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val authState = mutableStateOf<AuthState>(AuthState.Idle)

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            authState.value = AuthState.Loading
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    authState.value = AuthState.Success(user.uid)
                } else {
                    authState.value = AuthState.Error("Signup failed")
                }
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.localizedMessage ?: "Signup failed")
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authState.value = AuthState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                if (user != null) {
                    authState.value = AuthState.Success(user.uid)
                } else {
                    authState.value = AuthState.Error("Login failed")
                }
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.localizedMessage ?: "Login failed")
            }
        }
    }

    fun signOut() {
        auth.signOut()
        authState.value = AuthState.Idle
    }
}


