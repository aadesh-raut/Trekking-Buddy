package com.example.trekkingbuddy.ui.chat

data class ChatMessageModel(
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0L
)
