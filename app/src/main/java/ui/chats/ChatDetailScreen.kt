package com.example.trekkingbuddy.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import com.example.trekkingbuddy.ui.chat.MessageBubble


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: NavHostController,
    friendUid: String,
    friendName: String,
    viewModel: ChatViewModel = viewModel()
) {
    val myUid = FirebaseAuth.getInstance().currentUser!!.uid
    var message by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // 🔥 Start realtime listener
    LaunchedEffect(Unit) {
        viewModel.startListening(friendUid)
    }

    val messages = viewModel.messages.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(friendName) }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // 💬 Messages list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(messages) { msg ->
                    MessageBubble(
                        message = msg,
                        isMine = msg.senderId == myUid
                    )
                }
            }

            // ✍️ Message input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type message...") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (message.isNotBlank()) {
                            scope.launch {
                                ChatRepository.sendMessage(friendUid, message)
                                message = ""
                            }
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        }
    }
}




