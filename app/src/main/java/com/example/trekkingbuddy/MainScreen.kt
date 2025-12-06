package com.example.trekkingbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trekkingbuddy.ui.home.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
            modifier = androidx.compose.ui.Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen() }
            composable("friends") { com.example.trekkingbuddy.ui.friends.FriendsScreen() }
            composable("chat") { com.example.trekkingbuddy.ui.chat.ChatScreen() }
            composable("profile") { com.example.trekkingbuddy.ui.profile.ProfileScreen() }
        }
    }
}


