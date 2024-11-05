package com.example.practice3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.practice3.components.BottomNavigationBar
import com.example.practice3.components.navigationItems
import com.example.practice3.screens.ElementScreen
import com.example.practice3.screens.SettingsScreen
import com.example.practice3.screens.ListScreen
import com.example.practice3.screens.NotificationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val controller = rememberNavController()
    var currentRoute by remember { mutableStateOf("settings")}

    Scaffold(
        bottomBar = { BottomNavigationBar(controller, currentRoute) { currentRoute = it } }
    ) { innerPadding ->
        NavHost(navController = controller, startDestination = navigationItems.first().route) {
            composable("settings") {
                currentRoute = "settings"
                SettingsScreen()
            }
            composable("notification") {
                currentRoute = "notification"
                NotificationScreen(controller)
            }
            composable("list") {
                currentRoute = "list"
                ListScreen(controller)
            }
            composable("element/{elementId}") { backStackEntry ->
                val elementId = backStackEntry.arguments?.getString("elementId") ?: ""
                currentRoute = "element"
                ElementScreen(controller, elementId)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainScreen()
}