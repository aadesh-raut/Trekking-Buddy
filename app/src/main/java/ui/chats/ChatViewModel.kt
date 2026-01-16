package com.example.trekkingbuddy.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf

class ChatViewModel : ViewModel() {

    val messages = mutableStateOf<List<ChatMessageModel>>(emptyList())

    fun startListening(friendUid: String) {
        viewModelScope.launch {
            ChatRepository.listenForMessages(friendUid)
                .collectLatest {
                    messages.value = it
                }
        }
    }
}
