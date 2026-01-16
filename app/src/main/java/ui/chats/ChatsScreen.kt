package com.example.trekkingbuddy.ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trekkingbuddy.ui.friends.FriendsListViewModel
import com.example.trekkingbuddy.ui.friends.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    rootNavController: NavHostController,
    viewModel: FriendsListViewModel = viewModel()
) {

    // 🔥 Load friends as chat users
    LaunchedEffect(Unit) {
        viewModel.loadFriends()
    }

    val friends = viewModel.friends.value
    val isLoading = viewModel.isLoading.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chats") }
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
                    Text("No chats yet")
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
                        ChatUserRow(friend) {
                            // ✅ Navigate to chat detail
                            rootNavController.navigate(
                                "chat/${friend.uid}/${friend.username}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatUserRow(
    user: UserModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tap to chat",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}



