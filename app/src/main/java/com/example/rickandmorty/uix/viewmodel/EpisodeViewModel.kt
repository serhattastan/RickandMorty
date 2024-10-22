package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.entity.Episode
import com.example.rickandmorty.data.repo.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing and providing episode data to the UI.
 * This ViewModel interacts with the EpisodeRepository to fetch data from a data source (e.g., API).
 *
 * @param episodeRepository The repository responsible for fetching episode data.
 */
@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    // LiveData to hold the list of episodes, observed by the UI components.
    val episodeList = MutableLiveData<List<Episode>>()

    // Initializer block that fetches the episodes when the ViewModel is created.
    init {
        getEpisodes()
    }

    /**
     * Retrieves the list of episodes from the repository and updates the LiveData.
     * The data fetching is done within a coroutine to avoid blocking the main thread.
     */
    private fun getEpisodes() {
        // Launching a coroutine in the Main dispatcher to fetch episode data
        CoroutineScope(Dispatchers.Main).launch {
            // Fetching episodes from the repository and updating the LiveData
            episodeList.value = episodeRepository.getAllEpisodes()
        }
    }
}