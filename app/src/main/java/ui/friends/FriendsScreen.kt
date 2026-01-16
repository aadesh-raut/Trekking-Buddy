package com.example.trekkingbuddy.ui.friends

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    rootNavController: NavHostController,
    viewModel: FriendsListViewModel = viewModel()
) {
    // 🔥 Load friends when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadFriends()
    }

    val friends = viewModel.friends.value
    val isLoading = viewModel.isLoading.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Friends") },
                actions = {
                    IconButton(
                        onClick = {
                            // ✅ GLOBAL navigation via ROOT controller
                            rootNavController.navigate("suggested_friends")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonSearch,
                            contentDescription = "Suggested Friends"
                        )
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

            friends.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("You have no friends yet 🤝")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(friends) { friend ->
                        FriendRow(friend)
                    }
                }
            }
        }
    }
}

@Composable
fun FriendRow(friend: UserModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = friend.username,
                style = MaterialTheme.typography.titleMedium
            )

            if (friend.selectedLocation.isNotEmpty()) {
                Text(
                    text = "Trek: ${friend.selectedLocation}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}





