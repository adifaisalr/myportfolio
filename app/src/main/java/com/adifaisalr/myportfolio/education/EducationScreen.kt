package com.adifaisalr.myportfolio.education

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adifaisalr.myportfolio.Education

@Composable
fun EducationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: EducationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
    ) {
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
                                educationList = listOf(
                                    Education(
                                        "Politeknik Negeri Bandung",
                                        "Associate Degree",
                                        "Information Technology",
                                        "2008",
                                        "2011"
                                    )
                                ),
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
                    items(uiState.educationList) { education ->
                        EducationItem(
                            modifier = Modifier.padding(10.dp),
                            education = education
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EducationItem(
    modifier: Modifier,
    education: Education,
) {
    ElevatedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(text = "${education.universityName} (${education.degree})")
            Text(text = education.major)
            Text(text = "${education.startYear} - ${education.endYear}")
        }
    }
}

@Preview
@Composable
fun EducationItemPreview() {
    EducationItem(
        modifier = Modifier,
        education = Education(
            "Politeknik Negeri Bandung",
            "Associate Degree",
            "Information Technology",
            "2008",
            "2011"
        )
    )
}