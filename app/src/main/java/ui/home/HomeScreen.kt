package com.example.trekkingbuddy.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trekkingbuddy.ui.preferences.fetchSelectedLocationFromFirebase

@Composable
fun HomeScreen(navController: NavHostController) {

    var selectedLocation by remember { mutableStateOf<String?>(null) }

    // Load Firebase preference when screen opens
    LaunchedEffect(true) {
        selectedLocation = fetchSelectedLocationFromFirebase()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // -------------------------------
        // 🔵 TITLE
        // -------------------------------
        Text(
            "Home",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Welcome to Trekking Buddy!")

        Spacer(modifier = Modifier.height(25.dp))

        // -------------------------------
        // 🔵 SHOW SELECTED PREFERENCE
        // -------------------------------
        Text(
            text = "Your Trek Preference:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = selectedLocation ?: "No preference selected",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(25.dp))

        // -------------------------------
        // 🔵 PREFERENCES BUTTON
        // -------------------------------
        Button(
            onClick = { navController.navigate("preferences") }
        ) {
            Text("Preferences")
        }
    }
}

