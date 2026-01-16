package com.example.trekkingbuddy.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf

class ChatViewModel : ViewModel() {

    val messages = mutableStateOf<List<ChatMessageModel>>(emptyList())

    private var listenJob: Job? = null

    fun startListening(friendUid: String) {
        // 🔥 Cancel previous listener
        listenJob?.cancel()

        listenJob = viewModelScope.launch {
            ChatRepository.listenForMessages(friendUid)
                .collectLatest {
                    messages.value = it
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        listenJob?.cancel()
    }
}

