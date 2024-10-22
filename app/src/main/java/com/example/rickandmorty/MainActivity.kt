package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.uix.view.Transitions
import com.example.rickandmorty.uix.viewmodel.EpisodeViewModel
import com.example.rickandmorty.uix.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homeViewModel : HomeViewModel by viewModels()
    val episodeViewModel: EpisodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Transitions(homeViewModel, episodeViewModel)

            }
        }
    }
}