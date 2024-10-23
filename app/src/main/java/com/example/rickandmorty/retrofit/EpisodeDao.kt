package com.example.rickandmorty.retrofit

import com.example.rickandmorty.data.entity.Episode
import com.example.rickandmorty.data.entity.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * EpisodeDao is an interface that defines methods for interacting with the episode-related endpoints
 * of the Rick and Morty API. This interface is implemented by Retrofit to make network requests.
 */
interface EpisodeDao {

    /**
     * Retrieves a paginated list of episodes from the API.
     * This method sends an HTTP GET request to the "episode" endpoint to fetch data.
     * The request is asynchronous and must be called within a coroutine.
     *
     * @param page The page number to retrieve (for pagination).
     * @return EpisodeResponse The response from the API containing a list of episodes and pagination info.
     */
    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page") page: Int // The page parameter used to specify which page of results to retrieve from the API.
    ): EpisodeResponse // The response is mapped to the EpisodeResponse data class.

    /**
     * Retrieves a single episode by its ID from the API.
     * This method sends an HTTP GET request to the "episode/{id}" endpoint to fetch data.
     *
     * @param id The ID of the episode to retrieve.
     * @return Episode The response from the API containing the episode data.
     */
    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int // The ID parameter used to specify which episode to retrieve from the API.
    ): Episode

    /**
     * Retrieves multiple episodes by their IDs from the API.
     * This method sends an HTTP GET request to the "episode/{ids}" endpoint to fetch data.
     *
     * @param ids A comma-separated list of episode IDs to retrieve.
     * @return List<Episode> The response from the API containing a list of episodes.
     */
    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(
        @Path("ids") ids: String // The IDs parameter used to specify which episodes to retrieve from the API.
    ): List<Episode>

    /**
     * Filters episodes based on name or episode code.
     * This method sends an HTTP GET request to the "episode" endpoint with query parameters for filtering.
     *
     * @param name The name of the episode to filter by.
     * @param episode The code of the episode to filter by.
     * @return EpisodeResponse The response from the API containing filtered episodes and pagination info.
     */
    @GET("episode")
    suspend fun filterEpisodes(
        @Query("name") name: String? = null, // Optional name parameter to filter episodes by name.
        @Query("episode") episode: String? = null // Optional episode parameter to filter episodes by episode code.
    ): EpisodeResponse
}