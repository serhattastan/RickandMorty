package com.example.rickandmorty.data.datasource

import com.example.rickandmorty.data.entity.Location
import com.example.rickandmorty.retrofit.LocationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * LocationDataSource is responsible for fetching location data from the Rick and Morty API
 * by interacting with the LocationDao. It handles pagination to retrieve all locations across multiple pages.
 *
 * @param locationDao The DAO used to access the location-related API endpoints.
 */
class LocationDataSource(private val locationDao: LocationDao) {

    /**
     * Fetches all locations from the API by making paginated requests.
     * The method runs in the IO (Input/Output) coroutine dispatcher to perform network operations.
     * It loops through all available pages and aggregates the locations into a single list.
     *
     * @return List<Location> A list of all locations retrieved from the API.
     */
    suspend fun getAllLocations(): List<Location> = withContext(Dispatchers.IO) {
        val allLocations = mutableListOf<Location>() // List to hold all locations from all pages.
        var page = 1 // Start fetching locations from the first page.
        var hasNextPage: Boolean // Flag to track if there is a next page.

        // Loop through all available pages until no "next" page exists.
        do {
            val response = locationDao.getAllLocations(page) // Fetch the current page of locations.
            allLocations.addAll(response.results) // Add the locations from this page to the list.
            hasNextPage = response.info.next != null // Check if there is a next page.
            page++ // Move to the next page.
        } while (hasNextPage)

        // Return the complete list of locations.
        return@withContext allLocations
    }

    /**
     * Fetches a single location by its ID from the API.
     * The method runs in the IO coroutine dispatcher to perform network operations asynchronously.
     *
     * @param id The ID of the location to retrieve.
     * @return Location The location data for the specified ID.
     */
    suspend fun getLocationById(id: Int): Location = withContext(Dispatchers.IO) {
        return@withContext locationDao.getLocationById(id) // Fetch location by ID from the API.
    }

    /**
     * Filters locations based on the specified query parameters such as name, type, or dimension.
     * The method runs in the IO coroutine dispatcher to perform network operations asynchronously.
     *
     * @param name (Optional) The name of the location to filter by.
     * @param type (Optional) The type of the location to filter by.
     * @param dimension (Optional) The dimension of the location to filter by.
     * @return List<Location> A list of filtered locations based on the provided parameters.
     */
    suspend fun filterLocations(name: String?, type: String?, dimension: String?): List<Location> = withContext(Dispatchers.IO) {
        val response = locationDao.filterLocations(name, type, dimension) // Fetch filtered locations from the API.
        return@withContext response.results // Return the filtered results.
    }
}