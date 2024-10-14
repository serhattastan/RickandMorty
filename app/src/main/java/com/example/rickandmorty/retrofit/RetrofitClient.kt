package com.example.rickandmorty.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitClient is a utility class that provides a configured instance of Retrofit for making network requests.
 * It helps to centralize the setup of Retrofit, including the base URL and the converter factory.
 */
class RetrofitClient {

    companion object {

        /**
         * Creates and returns a Retrofit instance configured with the provided base URL.
         * Retrofit is used for making HTTP requests to RESTful APIs.
         *
         * @param baseUrl The base URL for the API that Retrofit will make requests to.
         * @return Retrofit An instance of Retrofit configured with the base URL and GsonConverterFactory for JSON parsing.
         */
        fun getClient(baseUrl: String): Retrofit {
            return Retrofit
                .Builder() // Starts building a Retrofit instance.
                .baseUrl(baseUrl) // Sets the base URL for API requests.
                .addConverterFactory(GsonConverterFactory.create()) // Adds GsonConverterFactory to handle JSON serialization/deserialization.
                .build() // Completes the Retrofit instance.
        }
    }
}