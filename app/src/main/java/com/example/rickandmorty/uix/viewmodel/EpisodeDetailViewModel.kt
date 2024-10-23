package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.data.entity.Episode
import com.example.rickandmorty.data.repo.CharacterRepository
import com.example.rickandmorty.data.repo.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing and providing episode details and its associated characters to the UI.
 * It retrieves data from both CharacterRepository and EpisodeRepository and exposes it as LiveData.
 *
 * @param episodeRepository The repository responsible for fetching episode data.
 * @param characterRepository The repository responsible for fetching character data.
 */
@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    // LiveData to hold the episode details and its associated characters.
    val episodeDetail = MutableLiveData<Episode>()
    val characterList = MutableLiveData<List<Character>>()

    /**
     * Retrieves an episode by its ID from the EpisodeRepository and fetches associated characters.
     * It first fetches the episode, then for each character URL in the episode, it fetches the character details.
     *
     * @param episodeId The ID of the episode to retrieve.
     */
    fun getEpisodeDetailsWithCharacters(episodeId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            // Fetch the episode details by ID
            val episode = episodeRepository.getEpisodeById(episodeId)
            episodeDetail.value = episode

            // Extract character IDs from episode's character URLs and fetch character details
            val characters = episode.characters.map { url ->
                // Assuming character URLs are in the format "https://rickandmortyapi.com/api/character/{id}"
                val characterId = url.substringAfterLast("/").toInt()
                characterRepository.getCharacterById(characterId)
            }
            characterList.value = characters
        }
    }
}