package com.example.rickandmorty.uix.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.uix.viewmodel.HomeViewModel

@Composable
fun Transitions(
    homeViewModel: HomeViewModel
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

    }

}