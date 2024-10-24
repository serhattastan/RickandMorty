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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import com.example.rickandmorty.data.entity.Episode
import com.example.rickandmorty.uix.uicomponent.BottomNavigationBar
import com.example.rickandmorty.uix.viewmodel.EpisodeViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Displays the list of episodes in a scrollable LazyColumn.
 * The UI supports searching episodes and navigating to episode details by dragging the episode card.
 *
 * @param viewModel The ViewModel responsible for providing the episode data.
 * @param navController NavController used to navigate to other screens (e.g., episode details).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeScreen(
    viewModel: EpisodeViewModel,
    navController: NavController
) {
    // State for handling the search bar visibility and text input
    val isSearching = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    val episodeList by viewModel.filteredEpisodeList.observeAsState(emptyList()) // List of filtered episodes

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Toggle between the search bar and the title
                    if (isSearching.value) {
                        TextField(
                            value = searchText.value,
                            onValueChange = {
                                searchText.value = it
                                viewModel.filterEpisodes(it) // Filter episodes based on search text
                            },
                            label = { Text(text = "Search") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White,
                            )
                        )
                    } else {
                        Text(text = "Rick and Morty Episodes", color = Color(0xFF1F8A70))
                    }
                },
                actions = {
                    // Search button toggles the search bar visibility
                    IconButton(onClick = {
                        isSearching.value = !isSearching.value
                        if (!isSearching.value) {
                            searchText.value = ""
                            viewModel.filterEpisodes("") // Reset search when exiting search mode
                        }
                    }) {
                        Icon(
                            imageVector = if (isSearching.value) Icons.Filled.Close else Icons.Filled.Search,
                            contentDescription = null,
                            tint = Color(0xFF1F8A70)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController, currentScreen = "EpisodeScreen") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
        ) {
            // LazyColumn to display the list of episodes
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(episodeList) { episode ->
                    EpisodeCard(episode = episode, navController = navController) // Display each episode as a card
                }
            }
        }
    }
}

/**
 * Displays an episode card that shows episode information and supports horizontal dragging.
 * Dragging the card halfway or more navigates to the episode detail screen.
 *
 * @param episode The episode data to be displayed.
 * @param navController NavController used to navigate to the episode detail screen.
 */
@Composable
fun EpisodeCard(episode: Episode, navController: NavController) {
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
                            // If the drag is far enough, navigate to the episode detail screen
                            if (abs(animatedOffsetX.value) > maxDragOffset * 0.75f) {
                                navController.navigate("EpisodeDetailScreen/${episode.id}")
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
            // Displays episode details in a column
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "${episode.id}.${episode.name}", style = MaterialTheme.typography.titleMedium, color = Color(0xFF1F8A70))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Air Date: ${episode.air_date}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1D1D1))
                Text(text = "Episode: ${episode.episode}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1D1D1))
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