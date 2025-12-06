package com.example.trekkingbuddy

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trekkingbuddy.ui.auth.LoginScreen
import com.example.trekkingbuddy.ui.auth.SignupScreen
import com.example.trekkingbuddy.ui.home.HomeScreen
import com.example.trekkingbuddy.ui.friends.FriendsScreen
import com.example.trekkingbuddy.ui.chat.ChatScreen
import com.example.trekkingbuddy.ui.profile.ProfileScreen
import com.example.trekkingbuddy.ui.admin.AdminScreen
import com.example.trekkingbuddy.ui.posts.PostsScreen
import com.example.trekkingbuddy.ui.preferences.PreferencesScreen

@Composable
fun TrekkingNavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // -------------------------
        // LOGIN SCREEN
        // -------------------------
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToSignup = {
                    navController.navigate("signup")
                },
                onLoginSuccess = {
                    navController.navigate("main_screen") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // -------------------------
        // SIGNUP SCREEN
        // -------------------------
        composable("signup") {
            SignupScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onSignupSuccess = {
                    navController.navigate("main_screen") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }

        // -------------------------
        // MAIN SCREEN (BOTTOM NAV)
        // -------------------------
        composable("main_screen") {
            MainScreen(navController)
        }

        // SUB-SCREENS
        composable("home") { HomeScreen() }
        composable("friends") { FriendsScreen() }
        composable("chat") { ChatScreen() }
        composable("profile") { ProfileScreen() }
        composable("posts") { PostsScreen() }
        composable("admin") { AdminScreen() }
        composable("preferences") { PreferencesScreen() }
    }
}






