package com.example.trekkingbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trekkingbuddy.ui.chat.ChatsScreen
import com.example.trekkingbuddy.ui.friends.FriendsScreen
import com.example.trekkingbuddy.ui.home.HomeScreen
import com.example.trekkingbuddy.ui.profile.ProfileScreen

@Composable
fun MainScreen(
    rootNavController: NavHostController
) {

    // Controller ONLY for bottom tabs
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController)
        }
    ) { paddingValues ->

        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("home") {
                HomeScreen(navController = rootNavController)
            }

            composable("friends") {
                FriendsScreen(rootNavController = rootNavController)
            }

            composable("chat") {
                ChatsScreen(rootNavController = rootNavController)
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}












