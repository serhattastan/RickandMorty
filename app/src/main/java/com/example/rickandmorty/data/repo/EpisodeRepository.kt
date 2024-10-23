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

    /**
     * Fetches a single episode by its ID from the data source.
     *
     * @param id The ID of the episode to retrieve.
     * @return Episode The episode retrieved from the data source.
     */
    suspend fun getEpisodeById(id: Int): Episode = eds.getEpisodeById(id)

    /**
     * Fetches multiple episodes by their IDs from the data source.
     *
     * @param ids A comma-separated list of episode IDs to retrieve.
     * @return List<Episode> The list of episodes retrieved from the data source.
     */
    suspend fun getEpisodesByIds(ids: String): List<Episode> = eds.getEpisodesByIds(ids)

    /**
     * Filters episodes based on name or episode code.
     *
     * @param name The name of the episode to filter by (optional).
     * @param episode The code of the episode to filter by (optional).
     * @return List<Episode> A list of filtered episodes retrieved from the data source.
     */
    suspend fun filterEpisodes(name: String? = null, episode: String? = null): List<Episode> = eds.filterEpisodes(name, episode)
}