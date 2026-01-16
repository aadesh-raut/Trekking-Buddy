package com.example.trekkingbuddy.ui.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.net.Uri


@Composable
fun PreferencesScreen(navController: NavHostController) {

    // ✅ NEW LIST OF 20 TREKKING LOCATIONS (MAHARASHTRA)
    val trekLocations = listOf(
        "Kalsubai Peak",
        "Harishchandragad",
        "Rajgad",
        "Torna Fort",
        "Lohagad Fort",
        "Visapur Fort",
        "Harihar Fort (Trimbakgad)",
        "Rajmachi Fort",
        "Andharban",
        "Ratangad Fort",
        "Korigad Fort",
        "Peb Fort (Vikatgad)",
        "Garbett Point (Matheran)",
        "Karnala Fort",
        "Tikona Fort",

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Select Your Preferred Trek",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ✅ SCROLLABLE LIST (important for 20 items)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(trekLocations) { location ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            // 👉 Navigate to location detail screen
                            val encodedLocation = Uri.encode(location)
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
}

