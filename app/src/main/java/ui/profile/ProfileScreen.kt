package com.example.trekkingbuddy.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.trekkingbuddy.data.ProfileRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("Beginner") }
    var expanded by remember { mutableStateOf(false) }
    var photoUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    var completedTreks by remember { mutableStateOf<List<CompletedTrek>>(emptyList()) }

    val experienceOptions = listOf("Beginner", "Intermediate", "Expert")

    // 📸 Image Picker
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            isUploading = true
            ProfileRepository.uploadProfileImage(
                imageUri = it,
                onSuccess = { url ->
                    photoUrl = url
                    isUploading = false
                },
                onFailure = {
                    isUploading = false
                }
            )
        }
    }

    // 🔥 Load profile & completed treks
    LaunchedEffect(Unit) {
        ProfileRepository.getUserProfile { profile ->
            name = profile.name
            bio = profile.bio
            experience = profile.experienceLevel
            photoUrl = profile.photoUrl
        }

        ProfileRepository.getCompletedTreks {
            completedTreks = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("My Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        // 📸 Profile Image
        Box(contentAlignment = Alignment.Center) {
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable { imagePicker.launch("image/*") }
                )
            } else {
                Surface(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable { imagePicker.launch("image/*") },
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("Add Photo")
                    }
                }
            }

            if (isUploading) {
                CircularProgressIndicator(modifier = Modifier.size(36.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 👤 Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 📝 Bio
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🎯 Experience Level
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = experience,
                onValueChange = {},
                readOnly = true,
                label = { Text("Experience Level") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                experienceOptions.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            experience = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 💾 Save Profile
        Button(
            onClick = {
                val profile = UserProfile(
                    name = name,
                    bio = bio,
                    experienceLevel = experience,
                    photoUrl = photoUrl
                )
                ProfileRepository.saveUserProfile(profile)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Profile")
        }

        // 🏔️ COMPLETED TREKS
        Spacer(modifier = Modifier.height(32.dp))

        Text("🏔️ Completed Treks", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(12.dp))

        if (completedTreks.isEmpty()) {
            Text("No treks completed yet")
        } else {
            completedTreks.forEach { trek ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(
                            trek.trekName,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            "Completed on: ${trek.completedOn.toDate()}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = {
                                ProfileRepository.removeCompletedTrek(trek.id) {
                                    ProfileRepository.getCompletedTreks {
                                        completedTreks = it
                                    }
                                }
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Remove")
                        }
                    }
                }
            }
        }
    }
}



