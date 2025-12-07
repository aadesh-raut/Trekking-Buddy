package com.example.trekkingbuddy.ui.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PreferencesScreen(navController: NavHostController) {

    // Sample list of trekking locations (you can add more later)
    val trekLocations = listOf(
        "Kedarkantha",
        "Har Ki Dun",
        "Triund",
        "Valley of Flowers",
        "Sandakphu"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Select Your Preferred Trek",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // List of trek buttons
        trekLocations.forEach { location ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        // Navigate to selected location detail
                        navController.navigate("location_detail/$location")
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = location,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
