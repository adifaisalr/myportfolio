@file:OptIn(ExperimentalMaterial3Api::class)

package com.adifaisalr.myportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adifaisalr.myportfolio.ui.theme.MyPortfolioTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    @Serializable
    object Profile

    @Serializable
    object FriendsList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Profile) {
                composable<Profile> { HomeScreen(navController = navController) }
                composable<FriendsList> { HomeScreen(navController = navController) }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var text: String by remember { mutableStateOf("Hello World") }
    MyPortfolioTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize()) {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(modifier = Modifier.requiredWidth(250.dp)) {
                        Text(text = "Drawer Title", modifier = Modifier.padding(16.dp))
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text(text = "Education") },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    text = "Education"
                                }
                            })
                        NavigationDrawerItem(
                            label = { Text(text = "Work Experience") },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    text = "Work Experience"
                                }
                            })
                    }
                },
                drawerState = drawerState,
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = "My Portfolio") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) {
                                            drawerState.open()
                                        } else {
                                            drawerState.close()
                                        }
                                    }
                                }) {
                                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                                }
                            })
                    }
                ) { innerPadding ->
                    EducationScreen(modifier = Modifier.padding(innerPadding), navController = navController)
                }
            }
        }
    }
}

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyPortfolioTheme {
        Greeting("Android")
    }
}