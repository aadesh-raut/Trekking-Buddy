package com.example.trekkingbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trekkingbuddy.ui.home.HomeScreen
import com.example.trekkingbuddy.ui.friends.FriendsScreen
import com.example.trekkingbuddy.ui.chat.ChatScreen
import com.example.trekkingbuddy.ui.profile.ProfileScreen
import com.example.trekkingbuddy.ui.preferences.PreferencesScreen
import com.example.trekkingbuddy.ui.preferences.LocationDetailScreen

@Composable
fun MainScreen(navController: NavHostController) {

    // 🔥 Bottom navigation controller
    val innerNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(innerNavController)
        }
    ) { paddingValues ->

        NavHost(
            navController = innerNavController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {

            // ------------------------
            // HOME
            // ------------------------
            composable("home") {
                // ✅ FIXED: pass ONLY root navController
                HomeScreen(navController)
            }

            // ------------------------
            // FRIENDS
            // ------------------------
            composable("friends") {
                FriendsScreen(navController)
            }

            // ------------------------
            // CHAT
            // ------------------------
            composable("chat") {
                ChatScreen()
            }

            // ------------------------
            // PROFILE
            // ------------------------
            composable("profile") {
                ProfileScreen()
            }

            // ------------------------
            // PREFERENCES
            // ------------------------
            composable("preferences") {
                PreferencesScreen(innerNavController)
            }

            // ------------------------
            // LOCATION DETAIL
            // ------------------------
            composable("location_detail/{locationName}") { backStackEntry ->
                val locationName =
                    backStackEntry.arguments?.getString("locationName") ?: ""

                LocationDetailScreen(
                    locationName = locationName,
                    navController = innerNavController,
                    onSave = {
                        innerNavController.popBackStack()
                    }
                )
            }
        }
    }
}





