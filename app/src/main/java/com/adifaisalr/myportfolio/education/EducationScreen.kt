package com.adifaisalr.myportfolio.education

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EducationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: EducationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.isError -> {
                Text(
                    text = "Error",
                    modifier = Modifier.padding(10.dp)
                )
            }

            uiState.educationList.isEmpty() -> {
                Row {
                    Text(
                        text = "Education Is Empty",
                        modifier = Modifier.padding(10.dp)
                    )
                    Button(onClick = {
                        viewModel.updateState(
                            EducationViewModel.UiState(
                                educationList = listOf("University", "High School"),
                                isError = false,
                                isLoading = false
                            )
                        )
                    }) {
                        Text(text = "Refresh")
                    }
                }
            }

            else -> {
                LazyColumn {
                    items(uiState.educationList) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}