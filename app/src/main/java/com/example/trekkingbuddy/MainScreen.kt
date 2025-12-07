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

            // HOME now receives navController
            composable("home") { HomeScreen(innerNavController) }

            composable("friends") { FriendsScreen() }
            composable("chat") { ChatScreen() }
            composable("profile") { ProfileScreen() }

            // Preference screen route
            composable("preferences") {
                PreferencesScreen(innerNavController)
            }

            // Trek location detail screen
            composable("location_detail/{locationName}") { backStackEntry ->
                val locationName = backStackEntry.arguments?.getString("locationName") ?: ""

                LocationDetailScreen(
                    locationName = locationName,
                    navController = innerNavController,
                    onSave = { selectedLocation ->
                        // For now: simply go back
                    }
                )
            }
        }
    }
}




