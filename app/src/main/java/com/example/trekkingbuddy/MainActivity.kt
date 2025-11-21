package com.example.trekkingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.trekkingbuddy.ui.theme.TrekkingBuddyTheme
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val bundle = Bundle().apply {
            putString("test_event", "firebase_connected")
        }
        firebaseAnalytics.logEvent("test_connection", bundle)

        // ✅ Set Compose UI
        setContent {
            TrekkingBuddyTheme {

                // Create ViewModel
                val viewModel = AuthViewModel()

                // Create NavController
                val navController = rememberNavController()

                // 🔥 IMPORTANT: connect ViewModel success callbacks to navigation
                viewModel.onLoginSuccess = {
                    navController.navigate("main_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                }

                viewModel.onSignupSuccess = {
                    navController.navigate("main_screen") {
                        popUpTo("signup") { inclusive = true }
                    }
                }

                // Start Navigation Graph
                TrekkingNavGraph(navController, viewModel)
            }
        }
    }
}




