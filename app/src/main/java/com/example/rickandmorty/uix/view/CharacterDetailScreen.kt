package com.example.rickandmorty.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.uix.uicomponent.ShimmerEffect
import com.example.rickandmorty.uix.viewmodel.CharacterDetailViewModel
import com.skydoves.landscapist.glide.GlideImage

/**
 * Displays detailed information about a specific character.
 * The screen includes a top bar, a back button, and character details such as name, status, species, gender, origin, and location.
 *
 * @param characterDetailViewModel The ViewModel responsible for fetching character details.
 * @param navController NavController used to navigate between screens.
 * @param characterId The ID of the character to fetch details for.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterDetailViewModel: CharacterDetailViewModel,
    navController: NavController,
    characterId: String
) {
    // Fetch character details when the screen is loaded
    LaunchedEffect(characterId) {
        characterDetailViewModel.getCharacterDetail(characterId.toInt())
    }

    // Observe the character data from the ViewModel
    val character by characterDetailViewModel.character.observeAsState()

    Scaffold(
        topBar = {
            // Displays the top bar with a title and a back button
            TopAppBar(
                title = { Text(text = "Character Details", color = Color(0xFF1F8A70)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFF121212))
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display character details if available, otherwise show a loading indicator
            character?.let {
                CharacterDetailContent(character = it)
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF1F8A70))
            }
        }
    }
}

/**
 * Displays the detailed content of a character, including an image and various attributes like status, species, gender, etc.
 *
 * @param character The character object containing detailed information to display.
 */
@Composable
fun CharacterDetailContent(character: Character) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Display character image inside a card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF202329)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Use GlideImage to display the character image
            GlideImage(
                imageModel = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                loading = {
                    ShimmerEffect(modifier = Modifier.fillMaxSize())
                }
            )
        }

        // Display character name with icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Character Icon",
                tint = Color(0xFF1F8A70),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1F8A70)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display character status with icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Status Icon",
                tint = when (character.status) {
                    "Alive" -> Color.Green
                    "Dead" -> Color.Red
                    else -> Color.Gray
                },
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Status: ${character.status}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFD1D1D1)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Display character species with icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = "Species Icon",
                tint = Color(0xFF1F8A70),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Species: ${character.species}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFD1D1D1)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Display character gender with icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Wc,
                contentDescription = "Gender Icon",
                tint = Color(0xFF1F8A70),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Gender: ${character.gender}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFD1D1D1)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Display character origin with icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Origin Icon",
                tint = Color(0xFF1F8A70),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Origin: ${character.origin.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFD1D1D1)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Display character location with icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Location Icon",
                tint = Color(0xFF1F8A70),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Last known location: ${character.location.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFD1D1D1)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display character episodes with icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Tv,
                contentDescription = "Episodes Icon",
                tint = Color(0xFF1F8A70),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Episodes: ${character.episode.joinToString { it.split("/").last() }}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFD1D1D1)
            )
        }
    }
}