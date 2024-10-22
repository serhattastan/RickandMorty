package com.example.rickandmorty.uix.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.uix.uicomponent.BottomNavigationBar
import com.example.rickandmorty.uix.uicomponent.ShimmerEffect
import com.example.rickandmorty.uix.viewmodel.HomeViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

/**
 * Displays the main home screen showing a list of Rick and Morty characters.
 * Handles user interactions, such as swiping between character cards and navigating to character details.
 *
 * @param viewModel The ViewModel responsible for providing character data.
 * @param navController NavController used to navigate between screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    // Observing character list from the ViewModel
    val characters by viewModel.characterList.observeAsState(emptyList())

    // Variables to track current character index and horizontal offset for swipe gesture
    var currentIndex by remember { mutableIntStateOf(0) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    // The scaffold contains the structure of the screen, including the top bar and bottom navigation.
    Scaffold(
        topBar = {
            // Displays the top bar with a title
            TopAppBar(
                title = { Text(text = "Rick and Morty Characters", color = Color(0xFF1F8A70)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF202329))
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController, currentScreen = "HomeScreen") }
    ) { paddingValues ->
        if (characters.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF121212))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    // If swipe is larger than threshold, change the character
                                    if (abs(offsetX) > 300) {
                                        val direction = offsetX.sign.toInt()
                                        val targetIndex = (currentIndex - direction).coerceIn(0, characters.size - 1)
                                        if (targetIndex != currentIndex) {
                                            animatedOffsetX.animateTo(
                                                targetValue = offsetX.sign * 1000f,
                                                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
                                            )
                                            currentIndex = targetIndex
                                        }
                                    }
                                    // Reset offset for next swipe
                                    offsetX = 0f
                                    animatedOffsetX.snapTo(0f)
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x
                                coroutineScope.launch {
                                    animatedOffsetX.snapTo(offsetX)
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (currentIndex < characters.size) {
                    val character = characters[currentIndex]
                    val nextIndex = (currentIndex + 1).coerceAtMost(characters.size - 1)
                    val nextCharacter = characters[nextIndex]

                    // Display current and next character cards
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CharacterCard(character = nextCharacter, offsetX = -animatedOffsetX.value * 0.2f, alpha = 0.5f) {}
                        CharacterCard(character = character, offsetX = animatedOffsetX.value, alpha = 1f) {
                            navController.navigate("CharacterDetailScreen/${character.id}")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Displays a card containing character information and handles swipe gestures for animation.
 *
 * @param character The character data to be displayed.
 * @param offsetX The horizontal offset to animate the card position.
 * @param alpha The transparency level of the card.
 * @param onClick The action to perform when the card is clicked.
 */
@Composable
fun CharacterCard(character: Character, offsetX: Float, alpha: Float, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .graphicsLayer {
                translationX = offsetX
                rotationZ = 0.1f * offsetX / 10
                this.alpha = alpha
                cameraDistance = 16 * density
            }
            .clickable { onClick() }, // Navigates to the character's detail screen when clicked
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3C3E44))
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            // Displaying character image
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
            // Displaying character details in a column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF202329), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(text = character.name, style = MaterialTheme.typography.titleMedium, color = Color(0xFF1F8A70))
                Spacer(modifier = Modifier.height(8.dp))
                CharacterAttributeRow(icon = Icons.Default.Person, label = "", value = character.species)
                CharacterAttributeRow(icon = Icons.Default.Accessibility, label = "", value = character.status)
                CharacterAttributeRow(icon = Icons.Default.Transgender, label = "", value = character.gender)
                CharacterAttributeRow(icon = Icons.Default.Place, label = "", value = character.location.name)
                CharacterAttributeRow(icon = Icons.Default.Bookmark, label = "", value = character.origin.name)
            }
        }
    }
}

/**
 * A row displaying a character attribute with an icon.
 *
 * @param icon The icon representing the attribute.
 * @param label The label for the attribute.
 * @param value The value of the attribute.
 */
@Composable
fun CharacterAttributeRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(imageVector = icon, contentDescription = "$label Icon", tint = Color(0xFF1F8A70))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$label: $value", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1D1D1))
    }
}