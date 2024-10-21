package com.example.rickandmorty.uix.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rickandmorty.R
import kotlinx.coroutines.delay

/**
 * SplashScreen is the initial screen displayed when the app is launched.
 * It contains a fading image and an animated title with scaling effect.
 * After a delay, it automatically navigates to the HomeScreen.
 *
 * @param navController NavController used to navigate to the HomeScreen after the splash screen ends.
 */
@Composable
fun SplashScreen(navController: NavController) {
    // Infinite animation for the image's alpha (opacity) effect
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Alpha animation, transitioning between 0.75f and 0.95f for a fade in/out effect
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // Infinite animation for the text's scale effect
    val textScale = rememberInfiniteTransition(label = "")
    val scaleAnim by textScale.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // LaunchedEffect to delay for 5 seconds before navigating to the HomeScreen
    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate("HomeScreen") {
            // Pop up to SplashScreen, removing it from the backstack to prevent going back to it
            popUpTo("SplashScreen") { inclusive = true }
        }
    }

    // Box layout to center the content on the screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)), // Background color of the splash screen
        contentAlignment = Alignment.Center
    ) {
        // Column to organize the image and text vertically
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Rick and Morty image with fading animation
            Image(
                painter = painterResource(id = R.drawable.img_rick_and_morty),
                contentDescription = "Rick and Morty",
                modifier = Modifier
                    .size(350.dp)
                    .alpha(alphaAnim), // Alpha animation applied here
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp)) // Space between image and text

            // Text with scaling animation
            BasicText(
                text = "Rick and Morty",
                modifier = Modifier
                    .scale(scaleAnim), // Scaling animation applied to the text
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F8A70),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp
                )
            )
        }
    }
}