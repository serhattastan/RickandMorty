package com.example.rickandmorty.retrofit

import com.example.rickandmorty.data.entity.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * LocationDao is an interface that defines methods for interacting with the location-related
 * endpoints of the Rick and Morty API. This interface is implemented by Retrofit to make
 * network requests to fetch location data.
 */
interface LocationDao {

    /**
     * Retrieves a paginated list of locations from the API.
     * This method sends an HTTP GET request to the "location" endpoint to fetch the location data.
     * The request is asynchronous and must be called within a coroutine.
     *
     * @param page The page number to retrieve (for pagination).
     * @return LocationResponse The response from the API containing a list of locations and pagination info.
     */
    @GET("location")
    suspend fun getAllLocations(
        @Query("page") page: Int // The page parameter used to specify which page of results to retrieve from the API.
    ): LocationResponse // The response is mapped to the LocationResponse data class.
}
