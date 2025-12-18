package com.example.trekkingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.trekkingbuddy.ui.theme.TrekkingBuddyTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --------------------------------------------
        // ✅ ENABLE FIRESTORE OFFLINE PERSISTENCE
        // --------------------------------------------
        FirebaseFirestore.getInstance().firestoreSettings =
            FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()

        // --------------------------------------------
        // 🔵 Firebase Analytics
        // --------------------------------------------
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val bundle = Bundle().apply {
            putString("test_event", "firebase_connected")
        }
        firebaseAnalytics.logEvent("test_connection", bundle)

        // --------------------------------------------
        // 🎨 Compose UI
        // --------------------------------------------
        setContent {
            TrekkingBuddyTheme {

                val navController = androidx.navigation.compose.rememberNavController()
                val viewModel = AuthViewModel()

                TrekkingNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}






