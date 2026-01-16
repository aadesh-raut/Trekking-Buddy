package com.example.trekkingbuddy.ui.friends

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun SuggestedFriendsScreen(
    navController: NavHostController,
    viewModel: FriendsViewModel = viewModel()
) {

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadSuggestedFriends()
    }

    val friends = viewModel.suggestedFriends.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.errorMessage.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Suggested Friends",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            friends.isEmpty() -> {
                Text(
                    text = "No trekkers found with the same selected trek yet.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                LazyColumn {
                    items(friends) { user ->
                        FriendItem(
                            user = user,
                            onAddClick = {
                                scope.launch {
                                    try {
                                        FriendRequestRepository.sendFriendRequest(
                                            receiverId = user.uid,
                                            receiverUsername = user.username
                                        )
                                        viewModel.refreshSuggestions()
                                    } catch (e: Exception) {
                                        // silently ignore / log later
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FriendItem(
    user: UserModel,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Trek: ${user.selectedLocation ?: "Not selected"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(onClick = onAddClick) {
                Text("Add")
            }
        }
    }
}






