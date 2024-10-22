package com.example.rickandmorty.uix.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.uix.viewmodel.CharacterDetailViewModel
import com.example.rickandmorty.uix.viewmodel.EpisodeViewModel
import com.example.rickandmorty.uix.viewmodel.HomeViewModel
import com.example.rickandmorty.uix.viewmodel.LocationViewModel

@Composable
fun Transitions(
    homeViewModel: HomeViewModel,
    episodeViewModel: EpisodeViewModel,
    locationViewModel: LocationViewModel,
    characterDetailViewModel: CharacterDetailViewModel
){
    val navController = rememberNavController()
    // NavHost, hangi ekranın gösterileceğini kontrol eder
    NavHost(navController = navController, startDestination = "SplashScreen"){
        // HomeScreen: Ana ekran
        composable("HomeScreen"){
            HomeScreen(homeViewModel, navController)
        }
        composable("SplashScreen"){
            SplashScreen(navController)
        }
        composable("EpisodeScreen"){
            EpisodeScreen(episodeViewModel, navController)
        }
        composable("LocationScreen"){
            LocationScreen(locationViewModel, navController)
        }
        composable("CharacterDetailScreen"){
            CharacterDetailScreen(characterDetailViewModel, navController)
        }

    }
}