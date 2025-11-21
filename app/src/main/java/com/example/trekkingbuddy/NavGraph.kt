package com.example.trekkingbuddy

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.trekkingbuddy.ui.home.HomeScreen
import com.example.trekkingbuddy.ui.friends.FriendsScreen
import com.example.trekkingbuddy.ui.chat.ChatScreen
import com.example.trekkingbuddy.ui.profile.ProfileScreen
import com.example.trekkingbuddy.ui.admin.AdminScreen
import com.example.trekkingbuddy.ui.posts.PostsScreen
import com.example.trekkingbuddy.ui.preferences.PreferencesScreen

@Composable
fun TrekkingNavGraph(viewModel: AuthViewModel) {

    val navController = rememberNavController()

    // -----------------------------
    // DECLARE SUCCESS CALLBACKS ONLY ONCE
    // -----------------------------
    viewModel.onLoginSuccess = {
        navController.navigate("main_screen") {
            popUpTo("login") { inclusive = true }
        }
    }

    viewModel.onSignupSuccess = {
        navController.navigate("main_screen") {
            popUpTo("login") { inclusive = true }
        }
    }

    // -----------------------------
    // NAVIGATION HOST
    // -----------------------------
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN SCREEN
        composable("login") {
            LoginScreen(viewModel) {
                navController.navigate("signup")
            }
        }

        // SIGNUP SCREEN
        composable("signup") {
            SignupScreen(viewModel) {
                navController.popBackStack()
            }
        }

        // MAIN SCREEN (BOTTOM NAVIGATION)
        composable("main_screen") {
            MainScreen(navController)
        }

        // APP SUB-SCREENS
        composable("home") { HomeScreen() }
        composable("friends") { FriendsScreen() }
        composable("chat") { ChatScreen() }
        composable("profile") { ProfileScreen() }

        composable("posts") { PostsScreen() }
        composable("admin") { AdminScreen() }
        composable("preferences") { PreferencesScreen() }
    }
}




