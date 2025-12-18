package com.example.trekkingbuddy.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trekkingbuddy.ui.preferences.fetchSelectedLocationFromFirebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {

    var selectedLocation by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch preference from Firebase
    LaunchedEffect(Unit) {
        selectedLocation = fetchSelectedLocationFromFirebase()
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trekking Buddy") },
                actions = {
                    IconButton(
                        onClick = {
                            // 🔔 Notifications navigation
                            navController.navigate("notifications")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome to Trekking Buddy!",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Your Trek Preference:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            when {
                isLoading -> CircularProgressIndicator()

                selectedLocation.isNullOrEmpty() -> {
                    Text(
                        text = "No preference selected",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    Text(
                        text = selectedLocation!!,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("preferences") }
            ) {
                Text("Preferences")
            }
        }
    }
}





