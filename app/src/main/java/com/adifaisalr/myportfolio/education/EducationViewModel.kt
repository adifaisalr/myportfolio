package com.adifaisalr.myportfolio.education

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EducationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateState(newState: UiState) {
        _uiState.update { newState }
    }

    data class UiState(
        var educationList: List<String> = listOf(),
        var isError: Boolean = false,
        var isLoading: Boolean = false,
    )
}