@file:OptIn(ExperimentalMaterial3Api::class)

package com.adifaisalr.myportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adifaisalr.myportfolio.ui.theme.MyPortfolioTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

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
                            Greeting("My Portfolio", Modifier.padding(innerPadding))
                        }
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