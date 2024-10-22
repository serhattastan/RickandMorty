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
 * ViewModel responsible for managing and providing both character and episode data to the UI.
 * It retrieves data from both CharacterRepository and EpisodeRepository and exposes it as LiveData.
 *
 * @param characterRepository The repository responsible for fetching character data.
 * @param episodeRepository The repository responsible for fetching episode data.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    // LiveData to hold the list of characters and episodes, observed by the UI components.
    val characterList = MutableLiveData<List<Character>>()
    val episodeList = MutableLiveData<List<Episode>>()

    // Initialization block that fetches both characters and episodes when the ViewModel is created.
    init {
        getCharacters()
        getEpisodes()
    }

    /**
     * Retrieves the list of characters from the CharacterRepository and updates the LiveData.
     * Characters are shuffled to provide randomness to the displayed list.
     * This operation is done within a coroutine to avoid blocking the main thread.
     */
    private fun getCharacters() {
        CoroutineScope(Dispatchers.Main).launch {
            characterList.value = characterRepository.getAllCharacters().shuffled()
        }
    }

    /**
     * Retrieves the list of episodes from the EpisodeRepository and updates the LiveData.
     * The data fetching is done within a coroutine to ensure it does not block the UI thread.
     */
    private fun getEpisodes() {
        CoroutineScope(Dispatchers.Main).launch {
            episodeList.value = episodeRepository.getAllEpisodes()
        }
    }
}