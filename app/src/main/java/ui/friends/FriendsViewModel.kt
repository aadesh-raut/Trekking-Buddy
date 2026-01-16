package com.example.trekkingbuddy.ui.friends

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    val suggestedFriends = mutableStateOf<List<UserModel>>(emptyList())
    val isLoading = mutableStateOf(false)

    fun loadSuggestedFriends() {
        viewModelScope.launch {
            isLoading.value = true
            suggestedFriends.value =
                FriendsRepository.fetchSuggestedFriends()

            isLoading.value = false
        }
    }



    // 🔥 Call this after accept / reject
    fun refreshSuggestions() {
        loadSuggestedFriends()
    }
}


