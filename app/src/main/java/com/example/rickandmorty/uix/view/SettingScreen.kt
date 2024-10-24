package com.example.rickandmorty.uix.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.R
import com.example.rickandmorty.uix.uicomponent.BottomNavigationBar
import com.example.rickandmorty.uix.viewmodel.SettingViewModel

/**
 * SettingScreen displays the settings options for the app.
 * It includes dark mode toggle, language selection, and social media links.
 *
 * @param settingViewModel ViewModel that handles the settings logic.
 * @param navController NavController to handle screen navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel,
    navController: NavController
) {
    // State to hold dark mode toggle and selected language
    var isDarkModeEnabled by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }

    // Scaffold to handle top and bottom bars
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings", color = Color(0xFF1F8A70)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController, currentScreen = "SettingScreen") }
    ) { paddingValues ->
        // Main content column to hold settings options
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp) // Add spacing between elements
        ) {
            // Dark Mode Toggle Section
            DarkModeToggle(isDarkModeEnabled) { isDarkModeEnabled = it }

            // Language Selection Section
            LanguageSelection(selectedLanguage) { selectedLanguage = it }

            // Social Media Icons Section
            SocialMediaSection()
        }
    }
}

/**
 * A composable function to display a toggle switch for dark mode.
 *
 * @param isDarkModeEnabled Boolean state indicating whether dark mode is enabled or not.
 * @param onToggle Callback function to toggle dark mode.
 */
@Composable
fun DarkModeToggle(isDarkModeEnabled: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Dark Mode", color = Color(0xFFD1D1D1), style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = isDarkModeEnabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF1F8A70))
        )
    }
}

/**
 * A composable function to display a language selection option with a dropdown menu.
 *
 * @param selectedLanguage The currently selected language.
 * @param onLanguageSelected Callback function when a language is selected.
 */
@Composable
fun LanguageSelection(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Language", color = Color(0xFFD1D1D1), style = MaterialTheme.typography.bodyLarge)

        // Dropdown Menu to select language
        Box {
            Text(
                text = selectedLanguage,
                color = Color(0xFF1F8A70),
                modifier = Modifier.clickable { expanded = !expanded }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {
                        onLanguageSelected("English")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Turkish") },
                    onClick = {
                        onLanguageSelected("Turkish")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("German") },
                    onClick = {
                        onLanguageSelected("German")
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * A composable function to display social media icons for following the app.
 */
@Composable
fun SocialMediaSection() {
    Column {
        Text(
            text = "Follow Us On:",
            color = Color(0xFF1F8A70),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SocialMediaIcon(
                iconRes = R.drawable.ic_launcher_foreground, // Replace with actual drawable icon
                description = "Twitter",
                onClick = { /* Handle Twitter navigation */ }
            )
            SocialMediaIcon(
                iconRes = R.drawable.ic_launcher_foreground, // Replace with actual drawable icon
                description = "LinkedIn",
                onClick = { /* Handle LinkedIn navigation */ }
            )
            SocialMediaIcon(
                iconRes = R.drawable.ic_launcher_foreground, // Replace with actual drawable icon
                description = "GitHub",
                onClick = { /* Handle GitHub navigation */ }
            )
        }
    }
}

/**
 * A composable function to display a clickable social media icon.
 *
 * @param iconRes Resource ID of the social media icon.
 * @param description Content description for accessibility purposes.
 * @param onClick Callback when the icon is clicked.
 */
@Composable
fun SocialMediaIcon(iconRes: Int, description: String, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = description,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() }
    )
}