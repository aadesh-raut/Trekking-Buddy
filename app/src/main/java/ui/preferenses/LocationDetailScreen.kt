package com.example.trekkingbuddy.ui.preferences

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// --------------------------------------------
// SAVE SELECTED LOCATION TO FIREBASE
// --------------------------------------------
fun saveLocationToFirebase(location: String) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    db.collection("users")
        .document(userId)
        .collection("preference")
        .document("data")
        .set(mapOf("selectedLocation" to location))
}

// --------------------------------------------
// CLEAR SELECTED LOCATION (DESELECT)
// --------------------------------------------
fun clearSelectedLocationFromFirebase() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    FirebaseFirestore.getInstance()
        .collection("users")
        .document(userId)
        .collection("preference")
        .document("data")
        .set(mapOf("selectedLocation" to null))
}

@Composable
fun LocationDetailScreen(
    locationName: String,
    navController: NavHostController,
    onSave: (String) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --------------------------------------------
        // 🔙 BACK ICON TOP-LEFT
        // --------------------------------------------
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --------------------------------------------
        // TITLE
        // --------------------------------------------
        Text(
            text = "About $locationName",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --------------------------------------------
        // DESCRIPTION TEXT
        // --------------------------------------------
        Text(
            text = "Description about the trekking location will come here.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --------------------------------------------
        // BUTTONS ROW
        // --------------------------------------------
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            // ❌ Cancel Button
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }

            // 👉 If location is already selected → show DESELECT button
            if (isSelected) {
                Button(
                    onClick = {
                        clearSelectedLocationFromFirebase()
                        isSelected = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Deselect")
                }

            } else {
                // 👉 If not selected → show SELECT button
                Button(
                    onClick = {
                        saveLocationToFirebase(locationName)
                        onSave(locationName)
                        isSelected = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Select")
                }
            }
        }
    }
}





