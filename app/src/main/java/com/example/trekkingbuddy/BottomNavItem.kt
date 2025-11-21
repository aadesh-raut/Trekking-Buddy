package com.example.trekkingbuddy

sealed class BottomNavItem(val route: String, val label: String) {
    object Home : BottomNavItem("home", "Home")
    object Friends : BottomNavItem("friends", "Friends")
    object Chat : BottomNavItem("chat", "Chat")
    object Profile : BottomNavItem("profile", "Profile")
}


