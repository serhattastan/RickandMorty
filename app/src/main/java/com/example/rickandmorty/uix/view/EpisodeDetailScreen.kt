package com.example.rickandmorty.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.uix.viewmodel.EpisodeDetailViewModel
import com.skydoves.landscapist.glide.GlideImage

/**
 * Displays the detailed view of an episode including episode information
 * and a list of characters appearing in the episode.
 *
 * @param episodeDetailViewModel The ViewModel responsible for fetching episode details.
 * @param navController NavController used for navigation between screens.
 * @param episodeId The ID of the episode to fetch details for.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    episodeDetailViewModel: EpisodeDetailViewModel,
    navController: NavController,
    episodeId: String
) {
    // Observing episode details and character list from ViewModel
    val episode by episodeDetailViewModel.episodeDetail.observeAsState()
    val characters by episodeDetailViewModel.characterList.observeAsState(emptyList())

    // Fetch episode details when the screen is loaded
    LaunchedEffect(episodeId) {
        episodeDetailViewModel.getEpisodeDetailsWithCharacters(episodeId.toInt())
    }

    // Scaffold provides the structure of the screen including the top bar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Episode Details", color = Color(0xFF1F8A70)) },
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
        // Column to display episode details and characters list
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            episode?.let { ep ->
                // Display episode name and air date
                Text(text = "Name: ${ep.name}", color = Color(0xFFD1D1D1), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Air Date: ${ep.air_date}", color = Color(0xFFD1D1D1), style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Display the list of characters in the episode
                Text(text = "Characters", color = Color(0xFF1F8A70), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(characters) { character ->
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

/**
 * Displays a card containing character details including an image, name, species,
 * and a heart icon indicating the character's status.
 *
 * @param character The character object containing detailed information to display.
 * @param onClick Callback when the character card is clicked.
 */
@Composable
fun CharacterCard(character: Character, onClick: () -> Unit) {
    // Determine the heart icon color based on the character's status
    val heartColor = when (character.status) {
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        else -> Color.Gray
    }

    // Card to display character details
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
            .background(Color(0xFF202329)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C3E44))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Ensure the heart icon is on the right
        ) {
            // Left side: Character image and details
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    imageModel = character.image,
                    contentDescription = character.name,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = character.name, style = MaterialTheme.typography.titleMedium, color = Color(0xFF1F8A70))
                    Text(text = character.species, style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1D1D1))
                }
            }

            // Right side: Heart icon indicating character's status
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Heart Icon",
                tint = heartColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}