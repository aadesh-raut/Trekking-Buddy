package com.example.trekkingbuddy.ui.friends

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FriendsListViewModel : ViewModel() {

    val friends = mutableStateOf<List<UserModel>>(emptyList())
    val isLoading = mutableStateOf(false)

    fun loadFriends() {
        viewModelScope.launch {
            isLoading.value = true
            friends.value = FriendsListRepository.fetchMyFriends()
            isLoading.value = false
        }
    }
}

