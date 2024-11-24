package com.example.practice3.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practice3.R
import com.example.practice3.classes.NavigationElement

val navigationItems = listOf(
    NavigationElement("Settings", "settings", R.drawable.home),
    NavigationElement("List", "list", R.drawable.list),
    NavigationElement("Notification", "notification", R.drawable.bell),
    NavigationElement("Profile", "profile", R.drawable.person)
)

@Composable
fun BottomNavigationBar(controller: NavHostController, currentRoute: String, onRouteChange: (String) -> Unit) {
    BottomNavigation(
        contentColor = Color.Black,
        backgroundColor = Color.Yellow,
        modifier = Modifier.height(48.dp)
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                icon = { Icon(painter = painterResource(id = item.resource), contentDescription = item.name) },
                label = { Text(item.name) },
                modifier = Modifier.size(32.dp).padding(top = 3.dp),
                onClick = {
                    controller.navigate(item.route) {
                        popUpTo(controller.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onRouteChange(item.route)
                }
            )
        }
    }
}