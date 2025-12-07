package com.example.trekkingbuddy.ui.preferences

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreferencesViewModel : ViewModel() {

    private val _selectedLocation = MutableStateFlow<String?>(null)
    val selectedLocation: StateFlow<String?> = _selectedLocation

    fun setLocation(location: String) {
        _selectedLocation.value = location
    }

    fun clearLocation() {
        _selectedLocation.value = null
    }
}


