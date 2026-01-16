package com.example.trekkingbuddy.ui.location

data class TrekLocationPoint(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)
