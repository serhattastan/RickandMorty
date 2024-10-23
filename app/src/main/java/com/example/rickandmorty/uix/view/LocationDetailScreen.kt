package com.example.rickandmorty.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.uix.viewmodel.LocationDetailViewModel

/**
 * Displays the detailed view of a location including location information
 * and a list of residents (characters) who have been seen at the location.
 *
 * @param locationDetailViewModel The ViewModel responsible for fetching location details.
 * @param navController NavController used for navigation between screens.
 * @param locationId The ID of the location to fetch details for.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    locationDetailViewModel: LocationDetailViewModel,
    navController: NavController,
    locationId: String
) {
    // Observing location details and resident character list from ViewModel
    val location by locationDetailViewModel.locationDetail.observeAsState()
    val residents by locationDetailViewModel.residentCharacters.observeAsState(emptyList())

    // Fetch location details when the screen is loaded
    LaunchedEffect(locationId) {
        locationDetailViewModel.getLocationDetailsWithResidents(locationId.toInt())
    }

    // Scaffold provides the structure of the screen including the top bar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Location Details", color = Color(0xFF1F8A70)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        }
    ) { paddingValues ->
        // Column to display location details and residents list
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            location?.let { loc ->
                // Display location name, type, and dimension
                Text(text = "Name: ${loc.name}", color = Color(0xFFD1D1D1), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Type: ${loc.type}", color = Color(0xFFD1D1D1), style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Dimension: ${loc.dimension}", color = Color(0xFFD1D1D1), style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Display the list of residents (characters) in the location
                Text(text = "Residents", color = Color(0xFF1F8A70), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(residents) { character ->
                        CharacterCard(character = character) {
                            navController.navigate("CharacterDetailScreen/${character.id}")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}