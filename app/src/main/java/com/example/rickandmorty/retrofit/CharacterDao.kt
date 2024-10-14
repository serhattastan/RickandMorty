package com.example.rickandmorty.retrofit

import com.example.rickandmorty.data.entity.CharacterResponse
import retrofit2.http.GET
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
}
