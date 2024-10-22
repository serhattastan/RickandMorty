package com.example.rickandmorty.data.repo

import com.example.rickandmorty.data.datasource.CharacterDataSource
import com.example.rickandmorty.data.entity.Character

/**
 * CharacterRepository is responsible for providing character data to other layers of the application.
 * It serves as a middle layer between the ViewModel and the data source (CharacterDataSource).
 * The repository helps in managing data operations and abstracts the source of the data (API, database, etc.).
 *
 * @param cds The CharacterDataSource instance used to fetch character data from the API.
 */
class CharacterRepository(private val cds: CharacterDataSource) {

    /**
     * Fetches all characters from the data source.
     * This method calls the CharacterDataSource to retrieve all characters by handling pagination.
     *
     * @return List<Character> A list of all characters retrieved from the data source.
     */
    suspend fun getAllCharacters(): List<Character> = cds.getAllCharacters()

    /**
     * Fetches filtered characters from the data source.
     * This method allows filtering characters by name or status.
     *
     * @param name The name of the character to filter by (optional).
     * @param status The status of the character to filter by (optional).
     * @return List<Character> A list of filtered characters retrieved from the data source.
     */
    suspend fun getFilteredCharacters(name: String?, status: String?): List<Character> = cds.getFilteredCharacters(name, status)

    /**
     * Fetches a character by its ID from the data source.
     * This method allows retrieving a specific character by its unique ID.
     *
     * @param id The ID of the character to retrieve.
     * @return Character The character data for the given ID.
     */
    suspend fun getCharacterById(id: Int): Character = cds.getCharacterById(id)
}