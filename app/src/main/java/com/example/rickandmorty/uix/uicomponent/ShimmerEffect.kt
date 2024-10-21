package com.example.rickandmorty.uix.uicomponent

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * ShimmerEffect is a custom composable function that provides a shimmer animation effect.
 * This effect is typically used as a placeholder while content is loading, giving users a sense of activity.
 *
 * @param modifier Modifier to be applied to the Box containing the shimmer effect, allowing customization from outside.
 */
@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    // Infinite transition that continuously animates the shimmer offset
    val shimmerTranslateAnim = rememberInfiniteTransition(label = "Shimmer Animation")

    // Animates the offset value to create the shimmer effect, moving it horizontally
    val shimmerOffset by shimmerTranslateAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // Duration of the shimmer animation
                easing = FastOutSlowInEasing // Smooth easing for a gradual animation
            ),
            repeatMode = RepeatMode.Restart // Restarts the animation once it reaches the end
        ),
        label = "Shimmer Offset"
    )

    // Defining the colors of the shimmer effect, transitioning between light gray shades
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f), // Light gray with some transparency
        Color.LightGray.copy(alpha = 0.2f), // Lighter gray with more transparency
        Color.LightGray.copy(alpha = 0.6f)  // Light gray again for the shimmering effect
    )

    // Brush that creates a linear gradient for the shimmer effect
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = androidx.compose.ui.geometry.Offset(x = shimmerOffset, y = 0f), // Start of the gradient
        end = androidx.compose.ui.geometry.Offset(x = shimmerOffset + 300f, y = 0f) // End of the gradient with an offset
    )

    // A Box component with the shimmer effect applied to its background using the linear gradient brush
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the shimmer effect box
            .background(brush) // Applying the gradient brush as the background of the box
    )
}