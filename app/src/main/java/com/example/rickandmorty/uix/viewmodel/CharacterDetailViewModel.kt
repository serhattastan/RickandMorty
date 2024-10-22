package com.example.rickandmorty.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.repo.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    characterRepository: CharacterRepository
): ViewModel() {
}