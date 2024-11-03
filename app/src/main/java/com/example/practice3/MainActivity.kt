package com.example.practice3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.practice3.components.BottomNavigationBar
import com.example.practice3.components.navigationItems
import com.example.practice3.screens.ElementScreen
import com.example.practice3.screens.HomeScreen
import com.example.practice3.screens.ListScreen
import com.example.practice3.screens.NotificationScreen
import com.example.practice3.ui.theme.Practice3Theme

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
    var currentRoute by remember { mutableStateOf("home")}

    Scaffold(
        bottomBar = { BottomNavigationBar(controller, currentRoute) { currentRoute = it } }
    ) { innerPadding ->
        NavHost(navController = controller, startDestination = navigationItems.first().route) {
            composable("home") {
                currentRoute = "home"
                HomeScreen()
            }
            composable("notification") {
                currentRoute = "notification"
                NotificationScreen()
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