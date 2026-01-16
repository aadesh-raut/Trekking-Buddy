package com.example.trekkingbuddy.ui.friends

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FriendRequestViewModel : ViewModel() {

    val incomingRequests = mutableStateOf<List<FriendRequestModel>>(emptyList())
    val isLoading = mutableStateOf(false)

    // 🔥 Load all pending incoming requests
    fun loadIncomingRequests() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                incomingRequests.value =
                    FriendRequestRepository.fetchIncomingRequests()
            } finally {
                isLoading.value = false
            }
        }
    }

    // ✅ Accept friend request
    fun acceptRequest(senderUid: String) {
        viewModelScope.launch {
            FriendRequestRepository.acceptRequest(senderUid)
            loadIncomingRequests() // refresh UI
        }
    }

    // ❌ Reject friend request
    fun rejectRequest(senderUid: String) {
        viewModelScope.launch {
            FriendRequestRepository.rejectRequest(senderUid)
            loadIncomingRequests() // refresh UI
        }
    }
}


