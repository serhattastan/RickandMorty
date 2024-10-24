package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.entity.Location
import com.example.rickandmorty.data.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing and providing location data to the UI.
 * It interacts with the LocationRepository to fetch data and exposes it as LiveData.
 */
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    // LiveData that holds the complete list of locations
    val locationList = MutableLiveData<List<Location>>()

    // LiveData that holds the filtered list of locations based on search query
    val filteredLocationList = MutableLiveData<List<Location>>()

    // Initialization block that automatically fetches locations when the ViewModel is created.
    init {
        getLocations()
    }

    /**
     * Retrieves the list of locations from the repository and updates the LiveData.
     * The data fetching is done within a coroutine to avoid blocking the main thread.
     */
    private fun getLocations() {
        // Launching a coroutine in the Main dispatcher to fetch location data
        viewModelScope.launch(Dispatchers.Main) {
            val locations = locationRepository.getAllLocations()
            locationList.value = locations
            filteredLocationList.value = locations // Initially, filtered list contains all locations
        }
    }

    /**
     * Filters the list of locations based on the search query.
     * Updates the filteredLocationList LiveData with the matching locations.
     *
     * @param query The search query to filter locations by name.
     */
    fun filterLocations(query: String) {
        filteredLocationList.value = if (query.isBlank()) {
            locationList.value
        } else {
            locationList.value?.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}