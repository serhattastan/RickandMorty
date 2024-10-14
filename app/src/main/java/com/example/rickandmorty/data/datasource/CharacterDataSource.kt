package com.example.rickandmorty.data.datasource

import com.example.rickandmorty.retrofit.CharacterDao
import com.example.rickandmorty.data.entity.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * CharacterDataSource is responsible for fetching character data from the Rick and Morty API
 * by interacting with the CharacterDao. It handles pagination to retrieve all characters across multiple pages.
 *
 * @param characterDao The DAO used to access the character-related API endpoints.
 */
class CharacterDataSource(private val characterDao: CharacterDao) {

    /**
     * Fetches all characters from the API by making paginated requests.
     * The method runs in the IO (Input/Output) coroutine dispatcher to perform network operations.
     * It loops through all available pages and aggregates the characters into a single list.
     *
     * @return List<Character> A list of all characters retrieved from the API.
     */
    suspend fun getAllCharacters(): List<Character> = withContext(Dispatchers.IO) {
        val allCharacters = mutableListOf<Character>() // List to hold all characters from all pages.
        var page = 1 // Starts from page 1.
        var hasNextPage: Boolean // Flag to check if there's a next page.

        // Loop through all available pages until no "next" page exists.
        do {
            val response = characterDao.getAllCharacters(page) // Fetch the current page of characters.
            allCharacters.addAll(response.results) // Add the characters from this page to the list.
            hasNextPage = response.info.next != null // Check if there is a next page.
            page++ // Move to the next page.
        } while (hasNextPage)

        // Return the complete list of characters.
        return@withContext allCharacters
    }
}