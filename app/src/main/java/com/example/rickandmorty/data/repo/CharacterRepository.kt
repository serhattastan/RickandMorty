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
}
