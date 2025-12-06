package com.example.trekkingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.trekkingbuddy.ui.theme.TrekkingBuddyTheme
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val bundle = Bundle().apply {
            putString("test_event", "firebase_connected")
        }
        firebaseAnalytics.logEvent("test_connection", bundle)

        // Set Compose UI
        setContent {
            TrekkingBuddyTheme {

                // Create Navigation Controller
                val navController = androidx.navigation.compose.rememberNavController()

                // Create ViewModel
                val viewModel = AuthViewModel()

                // Load Navigation Graph
                TrekkingNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }

    }
}





