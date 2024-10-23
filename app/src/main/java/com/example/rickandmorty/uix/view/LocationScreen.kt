package com.example.rickandmorty.uix.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.data.entity.Location
import com.example.rickandmorty.uix.uicomponent.BottomNavigationBar
import com.example.rickandmorty.uix.viewmodel.LocationViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Displays the screen showing a list of Rick and Morty locations.
 * Each location is shown in a draggable card, and users can swipe horizontally to navigate to location details.
 *
 * @param viewModel The ViewModel that provides the location data.
 * @param navController NavController used for navigation between screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    viewModel: LocationViewModel,
    navController: NavController
) {
    // Observing the list of locations from the ViewModel
    val locationList by viewModel.locationList.observeAsState(emptyList())

    // Scaffold provides the structure of the screen, including the top bar and bottom navigation bar
    Scaffold(
        topBar = {
            // Displays the top app bar with the title
            TopAppBar(
                title = { Text(text = "Rick and Morty Locations", color = Color(0xFF1F8A70)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController, currentScreen = "LocationScreen") }
    ) { paddingValues ->
        // Column layout to arrange content vertically
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
        ) {
            // LazyColumn to display the list of locations in a scrollable format
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Display each location using LocationCard
                items(locationList) { location ->
                    LocationCard(location = location, navController = navController)
                }
            }
        }
    }
}

/**
 * Displays a card containing information about a specific location.
 * The card supports horizontal drag gestures, allowing users to swipe to navigate to the location details.
 *
 * @param location The location data to be displayed.
 * @param navController NavController used to navigate to the location detail screen.
 */
@Composable
fun LocationCard(location: Location, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val animatedOffsetX = remember { Animatable(0f) }
    val maxDragOffset = 0.25f * 1080f // Assuming screen width is 1080 pixels, allowing drag up to 25% of the screen width

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer { translationX = animatedOffsetX.value } // Apply horizontal drag offset to the card
            .pointerInput(Unit) {
                // Detect horizontal drag gestures for swiping
                detectHorizontalDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            // If the drag is far enough, navigate to the location detail screen
                            if (abs(animatedOffsetX.value) > maxDragOffset * 0.75f) {
                                navController.navigate("LocationDetailScreen/${location.id}")
                            }
                            // Animate the card back to its original position
                            animatedOffsetX.animateTo(0f, animationSpec = tween(500))
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume() // Consume the gesture
                        coroutineScope.launch {
                            // Update the offset while keeping it within the allowed range
                            val newOffset = animatedOffsetX.value + dragAmount
                            if (newOffset >= -maxDragOffset && newOffset <= 0f) {
                                animatedOffsetX.snapTo(newOffset)
                            }
                        }
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C3E44)) // Card background color
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Displays location details in a column
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = location.name, style = MaterialTheme.typography.titleMedium, color = Color(0xFF1F8A70))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Type: ${location.type}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1D1D1))
                Text(text = "Dimension: ${location.dimension}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1D1D1))
                Text(text = "Residents: ${location.residents.size}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1D1D1))
            }
            // Displays a forward arrow if the card is dragged enough to the left
            if (animatedOffsetX.value < 0f && abs(animatedOffsetX.value) > maxDragOffset * 0.5f) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward, // Forward arrow icon
                    contentDescription = "ArrowForward",
                    tint = Color(0xFF1F8A70),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(32.dp)
                )
            }
        }
    }
}