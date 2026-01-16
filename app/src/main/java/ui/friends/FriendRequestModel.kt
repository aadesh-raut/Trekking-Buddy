package com.example.trekkingbuddy.ui.friends

data class FriendRequestModel(
    val senderUid: String = "",
    val senderUsername: String = "",
    val status: String = "pending"
)

