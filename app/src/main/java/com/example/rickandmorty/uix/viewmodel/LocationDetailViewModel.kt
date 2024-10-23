package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.data.entity.Location
import com.example.rickandmorty.data.repo.CharacterRepository
import com.example.rickandmorty.data.repo.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing and providing location details and associated residents' characters to the UI.
 * It retrieves data from both LocationRepository and CharacterRepository and exposes it as LiveData.
 *
 * @param locationRepository The repository responsible for fetching location data.
 * @param characterRepository The repository responsible for fetching character data.
 */
@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    // LiveData to hold the location details and its associated characters (residents).
    val locationDetail = MutableLiveData<Location>()
    val residentCharacters = MutableLiveData<List<Character>>()

    /**
     * Retrieves a location by its ID from the LocationRepository and fetches associated resident characters.
     * It first fetches the location, then for each resident's URL in the location, it fetches the character details.
     *
     * @param locationId The ID of the location to retrieve.
     */
    fun getLocationDetailsWithResidents(locationId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            // Fetch the location details by ID
            val location = locationRepository.getLocationById(locationId)
            locationDetail.value = location

            // Extract character IDs from the location's resident URLs and fetch each character by ID
            val residents = location.residents.map { url ->
                // Assuming resident URLs are in the format "https://rickandmortyapi.com/api/character/{id}"
                val characterId = url.substringAfterLast("/").toInt()
                characterRepository.getCharacterById(characterId)
            }
            residentCharacters.value = residents
        }
    }
}