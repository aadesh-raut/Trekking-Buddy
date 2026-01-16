package com.example.trekkingbuddy

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Friends : BottomNavItem("friends", "Friends", Icons.Filled.People)
    object Chat : BottomNavItem("chat", "Chat", Icons.Filled.Chat)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person)
}



