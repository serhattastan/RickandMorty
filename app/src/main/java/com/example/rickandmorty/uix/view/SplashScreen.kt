package com.example.rickandmorty.uix.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rickandmorty.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // rememberInfiniteTransition is used to create a continuously running animation.
    // We use this to animate the alpha and scale of the elements.
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Alpha animation for the image to create a subtle shimmer effect.
    // The image will continuously transition between 75% and 95% opacity.
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.75f, // Initial opacity value (slightly faded)
        targetValue = 0.95f,  // Final opacity value (almost fully opaque)
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing), // Linear easing for consistent speed
            repeatMode = RepeatMode.Reverse // The animation will reverse direction after finishing
        ), label = ""
    )

    // Scale animation for the text to create a smooth pulsating effect.
    // The text will scale between 1.0x (normal size) and 1.05x (slightly enlarged).
    val textScale = rememberInfiniteTransition(label = "")
    val scaleAnim by textScale.animateFloat(
        initialValue = 1f,   // Normal text size
        targetValue = 1.05f, // Slightly larger text size
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing), // Slow-in, fast-out easing for a smooth pulsation
            repeatMode = RepeatMode.Reverse // The animation will reverse after reaching the target size
        ), label = ""
    )

    // LaunchedEffect is used to delay navigation to the next screen.
    // It will trigger once when the composable is first composed.
    LaunchedEffect(Unit) {
        delay(5000) // Splash screen will be displayed for 5 seconds.

        // Navigate to the "HomeScreen" after the delay and remove "SplashScreen" from the back stack.
        // popUpTo("SplashScreen") removes SplashScreen so the user can't navigate back to it.
        navController.navigate("HomeScreen") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }

    // Box serves as a container for laying out children in a vertical and horizontal center alignment.
    Box(
        modifier = Modifier
            .fillMaxSize(), // The box takes up the entire screen
        contentAlignment = Alignment.Center // Aligns children (image and text) in the center of the screen
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centers content horizontally
            verticalArrangement = Arrangement.Center // Centers content vertically
        ) {
            // The image is displayed with the alpha animation applied to create a shimmer effect.
            Image(
                painter = painterResource(id = R.drawable.img_rick_and_morty), // Loads the image resource
                contentDescription = "Rick and Morty", // Content description for accessibility
                modifier = Modifier
                    .size(350.dp)  // Sets the size of the image
                    .alpha(alphaAnim), // Applies the alpha (transparency) animation
                contentScale = ContentScale.Fit // Scales the image to fit within its bounds without clipping
            )

            Spacer(modifier = Modifier.height(16.dp)) // Adds vertical spacing between the image and text

            // The text "Rick and Morty" is displayed with the scale animation applied for a pulsating effect.
            BasicText(
                text = "Rick and Morty", // The text to be displayed
                modifier = Modifier
                    .scale(scaleAnim), // Applies the scale animation for the pulsating effect
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold, // Makes the text bold
                    color = MaterialTheme.colorScheme.onBackground, // Sets the color based on the theme
                    textAlign = TextAlign.Center, // Centers the text horizontally
                    fontSize = 28.sp // Sets the font size of the text
                )
            )
        }
    }
}