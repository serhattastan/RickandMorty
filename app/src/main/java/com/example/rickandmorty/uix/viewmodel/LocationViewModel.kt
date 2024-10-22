package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.entity.Location
import com.example.rickandmorty.data.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing and providing location data to the UI.
 * It interacts with the LocationRepository to fetch data and exposes it as LiveData.
 *
 * @param locationRepository The repository responsible for fetching location data.
 */
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    // LiveData that holds the list of locations, observed by the UI components.
    val locationList = MutableLiveData<List<Location>>()

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
        CoroutineScope(Dispatchers.Main).launch {
            // Fetching locations from the repository and updating the LiveData
            locationList.value = locationRepository.getAllLocations()
        }
    }
}