package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.data.repo.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the data for character details.
 * It interacts with the CharacterRepository to fetch specific character information
 * and exposes the data to the UI through LiveData.
 *
 * @param characterRepository The repository that provides character data.
 */
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    // Backing property for character details. This is mutable only within the ViewModel.
    private val _character = MutableLiveData<Character>()

    // Publicly exposed immutable LiveData for observing character details.
    val character: LiveData<Character> get() = _character

    /**
     * Fetches the character details by ID and updates the LiveData.
     * This method is invoked when the CharacterDetailScreen is loaded.
     *
     * @param characterId The ID of the character to fetch details for.
     */
    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            try {
                // Fetch character details from the repository
                val characterDetail = characterRepository.getCharacterById(characterId)
                // Update the LiveData with the fetched character details
                _character.value = characterDetail
            } catch (e: Exception) {
                // Handle error (e.g., log it, update UI state, etc.)
                e.printStackTrace()
            }
        }
    }
}