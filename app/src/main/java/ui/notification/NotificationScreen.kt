package com.example.trekkingbuddy.ui.notifications

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
import com.example.trekkingbuddy.ui.friends.FriendRequestModel
import com.example.trekkingbuddy.ui.friends.FriendRequestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navController: NavHostController,
    viewModel: FriendRequestViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    // 🔥 Load requests when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadIncomingRequests()
    }

    val requests = viewModel.incomingRequests.value
    val isLoading = viewModel.isLoading.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            requests.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No new notifications")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
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

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "${request.senderUsername} sent you a friend request",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {

                TextButton(onClick = onReject) {
                    Text("Reject")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = onAccept) {
                    Text("Accept")
                }
            }
        }
    }
}




