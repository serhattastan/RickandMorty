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
}