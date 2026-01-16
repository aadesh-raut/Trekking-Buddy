package com.example.trekkingbuddy.ui.friends

data class UserModel(
    val uid: String = "",
    val username: String = "",
    val selectedLocation: String? = null // 🔥 nullable & safe
)

