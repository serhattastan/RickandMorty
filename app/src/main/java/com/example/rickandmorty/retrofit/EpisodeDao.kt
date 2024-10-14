package com.example.rickandmorty.retrofit

import com.example.rickandmorty.data.entity.EpisodeResponse
import retrofit2.http.GET
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
}
