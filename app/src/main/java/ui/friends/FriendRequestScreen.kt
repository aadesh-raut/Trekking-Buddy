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
import androidx.navigation.NavHostController

@Composable
fun FriendRequestsScreen(
    navController: NavHostController,
    viewModel: FriendRequestViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadIncomingRequests()
    }

    val requests = viewModel.incomingRequests.value
    val isLoading = viewModel.isLoading.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔙 Back
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Friend Requests",
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

            requests.isEmpty() -> {
                Text("No friend requests yet.")
            }

            else -> {
                LazyColumn {
                    items(requests) { request ->
                        FriendRequestItem(
                            request = request,
                            onAccept = {
                                viewModel.acceptRequest(request.senderUid)
                            },
                            onReject = {
                                viewModel.rejectRequest(request.senderUid)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FriendRequestItem(
    request: FriendRequestModel,
    onAccept: () -> Unit,
    onReject: () -> Unit
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

            Text(
                text = request.senderUsername,
                style = MaterialTheme.typography.titleMedium
            )

            Row {
                Button(onClick = onAccept) {
                    Text("Accept")
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(onClick = onReject) {
                    Text("Decline")
                }
            }
        }
    }
}

