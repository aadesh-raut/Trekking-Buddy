package com.example.trekkingbuddy.ui.profile

import com.google.firebase.Timestamp

data class CompletedTrek(
    val id: String = "",
    val trekName: String = "",
    val completedOn: Timestamp = Timestamp.now()
)

