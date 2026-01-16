package com.example.trekkingbuddy

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trekkingbuddy.ui.admin.AdminScreen
import com.example.trekkingbuddy.ui.auth.LoginScreen
import com.example.trekkingbuddy.ui.auth.SignupScreen
import com.example.trekkingbuddy.ui.chat.ChatDetailScreen
import com.example.trekkingbuddy.ui.location.LiveTrackingScreen
import com.example.trekkingbuddy.ui.location.LocationPermission
import com.example.trekkingbuddy.ui.notifications.NotificationScreen
import com.example.trekkingbuddy.ui.posts.PostsScreen
import com.example.trekkingbuddy.ui.friends.FriendRequestsScreen
import com.example.trekkingbuddy.ui.friends.SuggestedFriendsScreen

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
                    // ✅ ALWAYS go to main_screen
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
                    // ✅ ALWAYS go to main_screen
                    navController.navigate("main_screen") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }

        // ---------------- MAIN (BOTTOM NAV HOLDER) ----------------
        composable("main_screen") {
            MainScreen(rootNavController = navController)
        }

        // ---------------- CHAT DETAIL (GLOBAL) ----------------
        composable("chat/{friendUid}/{friendName}") { backStackEntry ->
            ChatDetailScreen(
                navController = navController,
                friendUid = backStackEntry.arguments?.getString("friendUid") ?: "",
                friendName = backStackEntry.arguments?.getString("friendName") ?: ""
            )
        }

        // ---------------- GLOBAL SCREENS ----------------
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

        // ---------------- LIVE TRACKING ----------------
        composable("live_tracking") {
            LocationPermission {
                LiveTrackingScreen()
            }
        }
    }
}




















