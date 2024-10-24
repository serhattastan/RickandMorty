package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.entity.Episode
import com.example.rickandmorty.data.repo.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    // LiveData to hold the complete list of episodes
    val episodeList = MutableLiveData<List<Episode>>()

    // LiveData to hold the filtered list of episodes based on search query
    val filteredEpisodeList = MutableLiveData<List<Episode>>()

    // Initializer block that fetches the episodes when the ViewModel is created.
    init {
        getEpisodes()
    }

    /**
     * Retrieves the list of episodes from the repository and updates the LiveData.
     * The data fetching is done within a coroutine to avoid blocking the main thread.
     */
    private fun getEpisodes() {
        viewModelScope.launch(Dispatchers.Main) {
            val episodes = episodeRepository.getAllEpisodes()
            episodeList.value = episodes
            filteredEpisodeList.value = episodes // Initially, filtered list contains all episodes
        }
    }

    /**
     * Filters the list of episodes based on the search query.
     * Updates the filteredEpisodeList LiveData with the matching episodes.
     *
     * @param query The search query to filter episodes by name.
     */
    fun filterEpisodes(query: String) {
        filteredEpisodeList.value = if (query.isBlank()) {
            episodeList.value
        } else {
            episodeList.value?.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}