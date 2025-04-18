package com.adifaisalr.myportfolio

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EducationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        data class Loaded(val list: List<String>) : UiState()
    }
}