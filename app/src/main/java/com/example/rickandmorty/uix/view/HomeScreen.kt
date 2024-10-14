package com.example.rickandmorty.uix.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.rickandmorty.uix.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    // ViewModel'deki karakter ve bölüm listelerini gözlemle
    val characters by viewModel.characterList.observeAsState(emptyList())
    val episodes by viewModel.episodeList.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        // Karakterleri gösteren liste
        Text(text = "Characters:")
        LazyColumn {
            items(characters) { character ->
                CharacterItem(character)
            }
        }

        // Bölümleri gösteren liste
        Text(text = "Episodes:")
        LazyColumn {
            items(episodes) { episode ->
                EpisodeItem(episode)
            }
        }
    }
}

@Composable
fun CharacterItem(character: com.example.rickandmorty.data.entity.Character) {
    Column {
        Text(text = "Name: ${character.name}")
        Text(text = "Species: ${character.species}")
        Text(text = "Status: ${character.status}")
    }
}

@Composable
fun EpisodeItem(episode: com.example.rickandmorty.data.entity.Episode) {
    Column {
        Text(text = "Episode: ${episode.name}")
        Text(text = "Air Date: ${episode.air_date}")
    }
}
