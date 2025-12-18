package com.example.trekkingbuddy

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trekkingbuddy.ui.auth.LoginScreen
import com.example.trekkingbuddy.ui.auth.SignupScreen
import com.example.trekkingbuddy.ui.home.HomeScreen
import com.example.trekkingbuddy.ui.friends.FriendsScreen
import com.example.trekkingbuddy.ui.friends.SuggestedFriendsScreen
import com.example.trekkingbuddy.ui.chat.ChatScreen
import com.example.trekkingbuddy.ui.profile.ProfileScreen
import com.example.trekkingbuddy.ui.admin.AdminScreen
import com.example.trekkingbuddy.ui.posts.PostsScreen
import com.example.trekkingbuddy.ui.preferences.PreferencesScreen
import com.example.trekkingbuddy.ui.preferences.LocationDetailScreen
import com.example.trekkingbuddy.ui.notifications.NotificationScreen

@Composable
fun TrekkingNavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

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

        composable("main_screen") {
            MainScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        // ✅ FIXED HERE
        composable("friends") {
            FriendsScreen(navController)
        }

        composable("chat") {
            ChatScreen()
        }

        composable("profile") {
            ProfileScreen()
        }

        composable("posts") {
            PostsScreen()
        }

        composable("admin") {
            AdminScreen()
        }

        composable("preferences") {
            PreferencesScreen(navController)
        }

        composable("location_detail/{locationName}") { backStackEntry ->
            val locationName =
                backStackEntry.arguments?.getString("locationName") ?: ""

            LocationDetailScreen(
                locationName = locationName,
                navController = navController,
                onSave = {
                    navController.popBackStack()
                }
            )
        }

        composable("notifications") {
            NotificationScreen(navController)
        }

        composable("suggested_friends") {
            SuggestedFriendsScreen(navController)
        }
    }
}











