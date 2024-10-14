package com.example.rickandmorty.retrofit

/**
 * ApiUtils is a utility class that provides methods to access different API data sources
 * using Retrofit. It contains methods to create instances of DAOs (Data Access Objects)
 * that interact with the Rick and Morty API to fetch character, episode, and location data.
 */
class ApiUtils {

    // Companion object allows these methods to be accessed without instantiating the ApiUtils class.
    companion object {

        // The base URL for the Rick and Morty API. This URL is used by Retrofit to make API calls.
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        /**
         * Provides an instance of CharacterDao.
         * CharacterDao contains methods to interact with the character-related endpoints of the API.
         *
         * @return CharacterDao - The interface implemented by Retrofit to handle character API calls.
         */
        fun getCharacterDao(): CharacterDao {
            // RetrofitClient initializes a Retrofit instance with the base URL and creates an implementation of CharacterDao.
            return RetrofitClient.getClient(BASE_URL).create(CharacterDao::class.java)
        }

        /**
         * Provides an instance of EpisodeDao.
         * EpisodeDao contains methods to interact with the episode-related endpoints of the API.
         *
         * @return EpisodeDao - The interface implemented by Retrofit to handle episode API calls.
         */
        fun getEpisodeDao(): EpisodeDao {
            // RetrofitClient initializes a Retrofit instance with the base URL and creates an implementation of EpisodeDao.
            return RetrofitClient.getClient(BASE_URL).create(EpisodeDao::class.java)
        }

        /**
         * Provides an instance of LocationDao.
         * LocationDao contains methods to interact with the location-related endpoints of the API.
         *
         * @return LocationDao - The interface implemented by Retrofit to handle location API calls.
         */
        fun getLocationDao(): LocationDao {
            // RetrofitClient initializes a Retrofit instance with the base URL and creates an implementation of LocationDao.
            return RetrofitClient.getClient(BASE_URL).create(LocationDao::class.java)
        }
    }
}