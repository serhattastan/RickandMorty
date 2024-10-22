package com.example.rickandmorty.retrofit

import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.data.entity.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * CharacterDao is an interface that manages character data retrieval from the Rick and Morty API.
 * This interface defines a method for fetching character data in a paginated manner.
 * The Retrofit library will implement this interface at runtime to make network requests.
 *
 * @see retrofit2.http.GET Used to define an HTTP GET request.
 * @see retrofit2.http.Query Used to specify query parameters in the API request.
 */
interface CharacterDao {

    /**
     * Fetches a paginated list of characters from the API.
     * This method is designed to retrieve a specific page of character data.
     * The method is asynchronous and should be called within a coroutine.
     *
     * @param page The page number to retrieve characters from (pagination).
     * @return CharacterResponse The response from the API containing character data and pagination info.
     */
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int): CharacterResponse

    /**
     * Fetches a filtered list of characters based on name and status.
     * This method allows searching and filtering characters.
     *
     * @param name The name of the character to filter by.
     * @param status The status of the character to filter by (e.g., "alive").
     * @return CharacterResponse The response containing filtered character data.
     */
    @GET("character")
    suspend fun getFilteredCharacters(@Query("name") name: String?, @Query("status") status: String?): CharacterResponse

    /**
     * Fetches a character by its ID.
     * This method allows retrieving a specific character by its unique ID.
     *
     * @param id The ID of the character to retrieve.
     * @return Character The character data for the given ID.
     */
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}