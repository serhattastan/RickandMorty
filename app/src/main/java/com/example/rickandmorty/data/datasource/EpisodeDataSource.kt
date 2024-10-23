package com.example.rickandmorty.data.datasource

import com.example.rickandmorty.data.entity.Episode
import com.example.rickandmorty.retrofit.EpisodeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * EpisodeDataSource is responsible for fetching episode data from the Rick and Morty API
 * by interacting with the EpisodeDao. It handles pagination to retrieve all episodes across multiple pages.
 *
 * @param episodeDao The DAO used to access the episode-related API endpoints.
 */
class EpisodeDataSource(private val episodeDao: EpisodeDao) {

    /**
     * Fetches all episodes from the API by making paginated requests.
     * The method runs in the IO (Input/Output) coroutine dispatcher to perform network operations.
     * It loops through all available pages and aggregates the episodes into a single list.
     *
     * @return List<Episode> A list of all episodes retrieved from the API.
     */
    suspend fun getAllEpisodes(): List<Episode> = withContext(Dispatchers.IO) {
        val allEpisodes = mutableListOf<Episode>() // List to store all episodes from all pages.
        var page = 1 // Start fetching episodes from the first page.
        var hasNextPage: Boolean // Flag to track if there is a next page.

        // Loop through all available pages until no "next" page exists.
        do {
            val response = episodeDao.getAllEpisodes(page) // Fetch the current page of episodes.
            allEpisodes.addAll(response.results) // Add the episodes from this page to the list.
            hasNextPage = response.info.next != null // Check if there is a next page.
            page++ // Move to the next page.
        } while (hasNextPage)

        // Return the complete list of episodes.
        return@withContext allEpisodes
    }

    /**
     * Fetches a single episode by its ID from the API.
     * The method runs in the IO coroutine dispatcher to perform network operations.
     *
     * @param id The ID of the episode to retrieve.
     * @return Episode The episode retrieved from the API.
     */
    suspend fun getEpisodeById(id: Int): Episode = withContext(Dispatchers.IO) {
        return@withContext episodeDao.getEpisodeById(id)
    }

    /**
     * Fetches multiple episodes by their IDs from the API.
     * The method runs in the IO coroutine dispatcher to perform network operations.
     *
     * @param ids A comma-separated list of episode IDs to retrieve.
     * @return List<Episode> The list of episodes retrieved from the API.
     */
    suspend fun getEpisodesByIds(ids: String): List<Episode> = withContext(Dispatchers.IO) {
        return@withContext episodeDao.getEpisodesByIds(ids)
    }

    /**
     * Filters episodes based on name or episode code.
     * The method runs in the IO coroutine dispatcher to perform network operations.
     *
     * @param name The name of the episode to filter by (optional).
     * @param episode The code of the episode to filter by (optional).
     * @return List<Episode> A list of filtered episodes retrieved from the API.
     */
    suspend fun filterEpisodes(name: String? = null, episode: String? = null): List<Episode> = withContext(Dispatchers.IO) {
        val response = episodeDao.filterEpisodes(name, episode)
        return@withContext response.results
    }
}