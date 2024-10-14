package com.example.rickandmorty.data.entity

data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)