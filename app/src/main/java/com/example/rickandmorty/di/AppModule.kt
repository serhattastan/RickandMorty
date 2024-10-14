package com.example.rickandmorty.di

import com.example.rickandmorty.data.datasource.CharacterDataSource
import com.example.rickandmorty.data.datasource.EpisodeDataSource
import com.example.rickandmorty.data.datasource.LocationDataSource
import com.example.rickandmorty.data.repo.CharacterRepository
import com.example.rickandmorty.data.repo.EpisodeRepository
import com.example.rickandmorty.data.repo.LocationRepository
import com.example.rickandmorty.retrofit.ApiUtils
import com.example.rickandmorty.retrofit.CharacterDao
import com.example.rickandmorty.retrofit.EpisodeDao
import com.example.rickandmorty.retrofit.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * AppModule provides dependency injection setup for the Rick and Morty app using Dagger Hilt.
 * It defines how dependencies like DAOs, data sources, and repositories are created and provided across the app.
 * All dependencies are installed in the SingletonComponent, meaning they will live throughout the app's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Provides the CharacterDao instance used to access character-related API data.
     *
     * @return CharacterDao The DAO that interacts with the character endpoints of the API.
     */
    @Provides
    @Singleton
    fun provideCharacterDao(): CharacterDao {
        return ApiUtils.getCharacterDao()
    }

    /**
     * Provides the EpisodeDao instance used to access episode-related API data.
     *
     * @return EpisodeDao The DAO that interacts with the episode endpoints of the API.
     */
    @Provides
    @Singleton
    fun provideEpisodeDao(): EpisodeDao {
        return ApiUtils.getEpisodeDao()
    }

    /**
     * Provides the LocationDao instance used to access location-related API data.
     *
     * @return LocationDao The DAO that interacts with the location endpoints of the API.
     */
    @Provides
    @Singleton
    fun provideLocationDao(): LocationDao {
        return ApiUtils.getLocationDao()
    }

    /**
     * Provides a CharacterDataSource instance, which fetches character data from the API.
     * The data source uses CharacterDao to make network calls and handle data retrieval.
     *
     * @param cdao The CharacterDao to interact with the API.
     * @return CharacterDataSource The data source that handles character data.
     */
    @Provides
    @Singleton
    fun provideCharacterDataSource(cdao: CharacterDao): CharacterDataSource {
        return CharacterDataSource(cdao)
    }

    /**
     * Provides an EpisodeDataSource instance, which fetches episode data from the API.
     * The data source uses EpisodeDao to make network calls and handle data retrieval.
     *
     * @param edao The EpisodeDao to interact with the API.
     * @return EpisodeDataSource The data source that handles episode data.
     */
    @Provides
    @Singleton
    fun provideEpisodeDataSource(edao: EpisodeDao): EpisodeDataSource {
        return EpisodeDataSource(edao)
    }

    /**
     * Provides a LocationDataSource instance, which fetches location data from the API.
     * The data source uses LocationDao to make network calls and handle data retrieval.
     *
     * @param ldao The LocationDao to interact with the API.
     * @return LocationDataSource The data source that handles location data.
     */
    @Provides
    @Singleton
    fun provideLocationDataSource(ldao: LocationDao): LocationDataSource {
        return LocationDataSource(ldao)
    }

    /**
     * Provides a CharacterRepository instance that interacts with the CharacterDataSource.
     * The repository abstracts the data layer and is used to provide character data to the ViewModel.
     *
     * @param cds The CharacterDataSource that fetches character data.
     * @return CharacterRepository The repository that manages character data.
     */
    @Provides
    @Singleton
    fun provideCharacterRepository(cds: CharacterDataSource): CharacterRepository {
        return CharacterRepository(cds)
    }

    /**
     * Provides an EpisodeRepository instance that interacts with the EpisodeDataSource.
     * The repository abstracts the data layer and is used to provide episode data to the ViewModel.
     *
     * @param eds The EpisodeDataSource that fetches episode data.
     * @return EpisodeRepository The repository that manages episode data.
     */
    @Provides
    @Singleton
    fun provideEpisodeRepository(eds: EpisodeDataSource): EpisodeRepository {
        return EpisodeRepository(eds)
    }

    /**
     * Provides a LocationRepository instance that interacts with the LocationDataSource.
     * The repository abstracts the data layer and is used to provide location data to the ViewModel.
     *
     * @param lds The LocationDataSource that fetches location data.
     * @return LocationRepository The repository that manages location data.
     */
    @Provides
    @Singleton
    fun provideLocationRepository(lds: LocationDataSource): LocationRepository {
        return LocationRepository(lds)
    }
}