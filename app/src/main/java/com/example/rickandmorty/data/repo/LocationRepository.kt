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
}
