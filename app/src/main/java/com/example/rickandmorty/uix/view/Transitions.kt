package com.example.rickandmorty.uix.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmorty.uix.viewmodel.CharacterDetailViewModel
import com.example.rickandmorty.uix.viewmodel.EpisodeDetailViewModel
import com.example.rickandmorty.uix.viewmodel.EpisodeViewModel
import com.example.rickandmorty.uix.viewmodel.HomeViewModel
import com.example.rickandmorty.uix.viewmodel.LocationDetailViewModel
import com.example.rickandmorty.uix.viewmodel.LocationViewModel

@Composable
fun Transitions(
    homeViewModel: HomeViewModel,
    episodeViewModel: EpisodeViewModel,
    locationViewModel: LocationViewModel,
    characterDetailViewModel: CharacterDetailViewModel,
    episodeDetailViewModel: EpisodeDetailViewModel,
    locationDetailViewModel: LocationDetailViewModel
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
        composable("CharacterDetailScreen/{characterId}",
            arguments = listOf(
                navArgument("characterId"){
                    type = NavType.StringType
                }
            )
        ){
            val characterId = it.arguments?.getString("characterId")
            CharacterDetailScreen(characterDetailViewModel, navController, characterId.toString())
        }
        composable("EpisodeDetailScreen/{episodeId}",
            arguments = listOf(
                navArgument("episodeId"){
                    type = NavType.StringType
                }
            )
        ){
            val episodeId = it.arguments?.getString("episodeId")
            EpisodeDetailScreen(episodeDetailViewModel, navController, episodeId.toString())
        }
        composable("LocationDetailScreen/{locationId}",
            arguments = listOf(
                navArgument("locationId"){
                    type = NavType.StringType
                }
            )
        ){
            val locationId = it.arguments?.getString("locationId")
            LocationDetailScreen(locationDetailViewModel, navController, locationId.toString())
        }

    }
}