package com.example.rickandmorty.uix.uicomponent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

/**
 * Displays the bottom navigation bar with navigation options for different screens.
 * This component helps users navigate between different sections of the app.
 *
 * @param navController The NavController used to navigate between different screens.
 * @param currentScreen The current screen being displayed, used to determine which item is selected.
 */
@Composable
fun BottomNavigationBar(navController: NavController, currentScreen: String) {
    // The main container for the navigation bar with a dark background color
    NavigationBar(containerColor = Color(0xFF202329)) {
        // Bottom navigation item for the Characters screen (Home)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }, // Home icon
            label = { Text("Characters") }, // Text label for the navigation item
            selected = currentScreen == "HomeScreen", // Marks this item as selected if the current screen matches
            onClick = { navController.navigate("HomeScreen") }, // Navigates to HomeScreen when clicked
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50), // Green color when selected
                selectedTextColor = Color(0xFF4CAF50), // Green text when selected
                unselectedIconColor = Color(0xFFB0BEC5), // Gray icon when unselected
                unselectedTextColor = Color(0xFFB0BEC5) // Gray text when unselected
            )
        )
        // Bottom navigation item for the Episodes screen
        NavigationBarItem(
            icon = { Icon(Icons.Default.Movie, contentDescription = "Episodes") }, // Movie icon
            label = { Text("Episodes") }, // Text label for the navigation item
            selected = currentScreen == "EpisodeScreen", // Marks this item as selected if the current screen matches
            onClick = { navController.navigate("EpisodeScreen") }, // Navigates to EpisodeScreen when clicked
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50), // Green color when selected
                selectedTextColor = Color(0xFF4CAF50), // Green text when selected
                unselectedIconColor = Color(0xFFB0BEC5), // Gray icon when unselected
                unselectedTextColor = Color(0xFFB0BEC5) // Gray text when unselected
            )
        )
        // Bottom navigation item for the Locations screen
        NavigationBarItem(
            icon = { Icon(Icons.Default.Place, contentDescription = "Locations") }, // Place icon
            label = { Text("Locations") }, // Text label for the navigation item
            selected = currentScreen == "LocationScreen", // Marks this item as selected if the current screen matches
            onClick = { navController.navigate("LocationScreen") }, // Navigates to Locations screen when clicked
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50), // Green color when selected
                selectedTextColor = Color(0xFF4CAF50), // Green text when selected
                unselectedIconColor = Color(0xFFB0BEC5), // Gray icon when unselected
                unselectedTextColor = Color(0xFFB0BEC5) // Gray text when unselected
            )
        )
        // Bottom navigation item for the Settings screen
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }, // Settings icon
            label = { Text("Settings") }, // Text label for the navigation item
            selected = currentScreen == "SettingScreen", // Marks this item as selected if the current screen matches
            onClick = { navController.navigate("SettingScreen") }, // Navigates to Settings screen when clicked
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50), // Green color when selected
                selectedTextColor = Color(0xFF4CAF50), // Green text when selected
                unselectedIconColor = Color(0xFFB0BEC5), // Gray icon when unselected
                unselectedTextColor = Color(0xFFB0BEC5) // Gray text when unselected
            )
        )
    }
}