package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.entity.Character
import com.example.rickandmorty.data.entity.Episode
import com.example.rickandmorty.data.repo.CharacterRepository
import com.example.rickandmorty.data.repo.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository
): ViewModel(){
    val characterList = MutableLiveData<List<Character>>()
    val episodeList = MutableLiveData<List<Episode>>()

    init {
        getCharacters()
        getEpisodes()
    }

    private fun getCharacters() {
        CoroutineScope(Dispatchers.Main).launch {
            characterList.value = characterRepository.getAllCharacters()
        }
    }
    private fun getEpisodes() {
        CoroutineScope(Dispatchers.Main).launch {
            episodeList.value = episodeRepository.getAllEpisodes()
        }
    }


}