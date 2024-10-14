package com.example.rickandmorty.data.repo

import com.example.rickandmorty.data.datasource.EpisodeDataSource
import com.example.rickandmorty.data.entity.Episode

/**
 * EpisodeRepository is responsible for providing episode data to other layers of the application.
 * It acts as a middle layer between the ViewModel and the data source (EpisodeDataSource).
 * The repository helps in managing data operations and abstracts the data source (API, database, etc.).
 *
 * @param eds The EpisodeDataSource instance used to fetch episode data from the API.
 */
class EpisodeRepository(private val eds: EpisodeDataSource) {

    /**
     * Fetches all episodes from the data source.
     * This method calls the EpisodeDataSource to retrieve all episodes by handling pagination.
     *
     * @return List<Episode> A list of all episodes retrieved from the data source.
     */
    suspend fun getAllEpisodes(): List<Episode> = eds.getAllEpisodes()
}
