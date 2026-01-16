package com.example.trekkingbuddy

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.trekkingbuddy.ui.admin.AdminScreen
import com.example.trekkingbuddy.ui.auth.LoginScreen
import com.example.trekkingbuddy.ui.auth.SignupScreen
import com.example.trekkingbuddy.ui.chat.ChatDetailScreen
import com.example.trekkingbuddy.ui.notifications.NotificationScreen
import com.example.trekkingbuddy.ui.posts.PostsScreen
import com.example.trekkingbuddy.ui.friends.FriendRequestsScreen
import com.example.trekkingbuddy.ui.friends.SuggestedFriendsScreen
import com.example.trekkingbuddy.ui.preferences.LocationDetailScreen
import com.example.trekkingbuddy.ui.preferences.PreferencesScreen
import com.example.trekkingbuddy.ui.location.LocationPermission
import com.example.trekkingbuddy.ui.location.LiveTrackingScreen

@Composable
fun TrekkingNavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ---------------- AUTH ----------------
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

        // ---------------- MAIN ----------------
        composable("main_screen") {
            MainScreen(rootNavController = navController)
        }

        // ---------------- CHAT DETAIL ----------------
        composable("chat/{friendUid}/{friendName}") { backStackEntry ->
            ChatDetailScreen(
                navController = navController,
                friendUid = backStackEntry.arguments?.getString("friendUid") ?: "",
                friendName = backStackEntry.arguments?.getString("friendName") ?: ""
            )
        }

        // ---------------- LOCATION DETAIL (✅ FIX ADDED) ----------------
        composable(
            route = "location_detail/{locationName}",
            arguments = listOf(
                navArgument("locationName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val locationName =
                backStackEntry.arguments?.getString("locationName")
                    ?: return@composable

            LocationDetailScreen(
                locationName = locationName,
                navController = navController
            )
        }

        // ---------------- GLOBAL ----------------
        composable("notifications") {
            NotificationScreen(navController)
        }

        composable("posts") {
            PostsScreen()
        }

        composable("admin") {
            AdminScreen()
        }

        composable("suggested_friends") {
            SuggestedFriendsScreen(navController)
        }

        composable("friend_requests") {
            FriendRequestsScreen(navController)
        }

        // ---------------- PREFERENCES ----------------
        composable("preferences") {
            PreferencesScreen(navController = navController)
        }

        // ---------------- LIVE TRACKING ----------------
        // ---------------- LIVE TRACKING ----------------
        composable("live_tracking") {
            LocationPermission {
                LiveTrackingScreen()
            }
        }




    }
}






















