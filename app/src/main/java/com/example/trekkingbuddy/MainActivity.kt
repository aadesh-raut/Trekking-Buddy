package com.example.trekkingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.trekkingbuddy.ui.theme.TrekkingBuddyTheme
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {




    private lateinit var firebaseAnalytics: FirebaseAnalytics  // ✅ Firebase Analytics variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // ✅ Log a test event to confirm Firebase is working
        val bundle = Bundle().apply {
            putString("test_event", "firebase_connected")
        }
        firebaseAnalytics.logEvent("test_connection", bundle)

        // ✅ Show your Auth (Login/Signup) UI instead of the default "Hello Android"
        setContent {
            TrekkingBuddyTheme {
                val viewModel = AuthViewModel()
                TrekkingNavGraph(viewModel)
            }
        }
    }
}



