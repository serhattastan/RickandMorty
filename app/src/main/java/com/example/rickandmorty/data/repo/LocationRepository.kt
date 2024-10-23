package com.example.rickandmorty.data.repo

import com.example.rickandmorty.data.datasource.LocationDataSource
import com.example.rickandmorty.data.entity.Location

/**
 * LocationRepository is responsible for providing location data to other layers of the application.
 * It acts as a middle layer between the ViewModel and the data source (LocationDataSource).
 * The repository helps in managing data operations and abstracts the data source (API, database, etc.).
 *
 * @param lds The LocationDataSource instance used to fetch location data from the API.
 */
class LocationRepository(private val lds: LocationDataSource) {

    /**
     * Fetches all locations from the data source.
     * This method calls the LocationDataSource to retrieve all locations by handling pagination.
     *
     * @return List<Location> A list of all locations retrieved from the data source.
     */
    suspend fun getAllLocations(): List<Location> = lds.getAllLocations()

    /**
     * Fetches a single location by its ID from the data source.
     * This method calls the LocationDataSource to retrieve a specific location by its ID.
     *
     * @param id The ID of the location to retrieve.
     * @return Location The location data for the given ID.
     */
    suspend fun getLocationById(id: Int): Location = lds.getLocationById(id)

    /**
     * Filters locations based on provided query parameters.
     * This method calls the LocationDataSource to filter locations by name, type, or dimension.
     *
     * @param name (Optional) The name of the location to filter by.
     * @param type (Optional) The type of the location to filter by.
     * @param dimension (Optional) The dimension of the location to filter by.
     * @return List<Location> A list of locations that match the provided filter criteria.
     */
    suspend fun filterLocations(name: String?, type: String?, dimension: String?): List<Location> = lds.filterLocations(name, type, dimension)
}