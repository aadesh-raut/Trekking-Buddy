package com.example.trekkingbuddy.ui.friends

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    val suggestedFriends = mutableStateOf<List<UserModel>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun loadSuggestedFriends() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                errorMessage.value = null

                suggestedFriends.value =
                    FriendsRepository.fetchSuggestedFriends()

            } catch (e: Exception) {
                errorMessage.value = "Failed to load suggested friends"
                suggestedFriends.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun refreshSuggestions() {
        loadSuggestedFriends()
    }
}



