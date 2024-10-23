package com.example.rickandmorty.retrofit

import com.example.rickandmorty.data.entity.Location
import com.example.rickandmorty.data.entity.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    /**
     * Retrieves a single location by its ID from the API.
     * This method sends an HTTP GET request to the "location/{id}" endpoint to fetch the location data.
     * The request is asynchronous and must be called within a coroutine.
     *
     * @param id The ID of the location to retrieve.
     * @return Location The location data retrieved from the API.
     */
    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int // The ID parameter used to specify which location to retrieve.
    ): Location // The response is mapped to the Location data class.

    /**
     * Filters locations by optional parameters such as name, type, and dimension.
     * This method sends an HTTP GET request to the "location" endpoint with query parameters to filter the results.
     *
     * @param name (Optional) The name of the location to filter by.
     * @param type (Optional) The type of the location to filter by.
     * @param dimension (Optional) The dimension of the location to filter by.
     * @return LocationResponse The response from the API containing a list of filtered locations.
     */
    @GET("location")
    suspend fun filterLocations(
        @Query("name") name: String? = null,  // Optional name filter.
        @Query("type") type: String? = null,  // Optional type filter.
        @Query("dimension") dimension: String? = null // Optional dimension filter.
    ): LocationResponse // The response is mapped to the LocationResponse data class.
}